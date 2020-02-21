package com.sleepyduck.geometry

import kotlin.contracts.contract
import kotlin.math.*

private const val TOTAL_ANGLE_DEG = 180.0
private const val DEG_TO_RAD = PI / 180.0
private const val RAD_TO_DEG = 180.0 / PI

/**
 *                (ax,ay)
 *                  /\                     |
 *                /    \                   |
 *              / alpha  \                 |
 *          B /            \  C          h |
 *          /                \             |
 *        /        Area        \           |
 *      / gamma             beta \         |
 *    /____________________________\       |
 * (cx,cy)          A          (bx,by)
 */
class Triangle(
    var a: Double? = null,
    var b: Double? = null,
    var c: Double? = null,
    var alpha: Double? = null,
    var beta: Double? = null,
    var gamma: Double? = null,
    var area: Double? = null,
    var height: Double? = null,
    var ax: Double? = null,
    var ay: Double? = null,
    var bx: Double? = null,
    var by: Double? = null,
    var cx: Double? = null,
    var cy: Double? = null
) {

    val isValid: Boolean
        get() = listOfNotNull(a, b, c, alpha, beta, gamma, area, height, ax, ay, bx, by, cx, cy).filter { it.isNaN() }.none()
                && alpha.isValidAngle() && beta.isValidAngle() && gamma.isValidAngle()

    init {
        for (i in 0..13) {
            /* if (a == null) */ a = calcSide(alpha, gamma, beta, b, c, area, height, Point(ax, ay), Point(cx, cy), Point(bx, by)) ?: a
            /* if (b == null) */ b = calcSide(beta, alpha, gamma, c, a, area, null, Point(bx, by), Point(ax, ay), Point(cx, cy)) ?: b
            /* if (c == null) */ c = calcSide(gamma, beta, alpha, a, b, area, null, Point(cx, cy), Point(ax, ay), Point(bx, by)) ?: c
            /* if (alpha == null) */ alpha = calcAngle(beta, gamma, a, c, b, area, height, Point(ax, ay), Point(bx, by), Point(cx, cy)) ?: alpha
            /* if (beta == null) */ beta = calcAngle(gamma, alpha, b, a, c, area, null, Point(bx, by), Point(cx, cy), Point(ax, ay)) ?: beta
            /* if (gamma == null) */ gamma = calcAngle(alpha, beta, c, b, a, area, null, Point(cx, cy), Point(ax, ay), Point(bx, by)) ?: gamma
            /* if (area == null) */ area = calcArea(a, b, c, alpha, beta, gamma, height, Point(cx, cy), Point(ax, ay), Point(bx, by)) ?: area
            /* if (height == null) */ height = calcHeight(a, b, c, alpha, beta, gamma, area, Point(ax, ay), Point(bx, by), Point(cx, cy)) ?: height
            /* if (ax == null) */ ax = calcPoint(Point(ax, ay), Point(bx, by), Point(cx, cy), a, c, b, alpha, beta, gamma, area, height)?.x ?: ax
            /* if (ay == null) */ ay = calcPoint(Point(ax, ay), Point(bx, by), Point(cx, cy), a, c, b, alpha, beta, gamma, area, height)?.y ?: ay
            /* if (bx == null) */ bx = calcPoint(Point(bx, by), Point(cx, cy), Point(ax, ay), b, a, c, beta, gamma, alpha, area, null)?.x ?: bx
            /* if (by == null) */ by = calcPoint(Point(bx, by), Point(cx, cy), Point(ax, ay), b, a, c, beta, gamma, alpha, area, null)?.y ?: by
            /* if (cx == null) */ cx = calcPoint(Point(cx, cy), Point(ax, ay), Point(bx, by), c, b, a, gamma, alpha, beta, area, null)?.x ?: cx
            /* if (cy == null) */ cy = calcPoint(Point(cx, cy), Point(ax, ay), Point(bx, by), c, b, a, gamma, alpha, beta, area, null)?.y ?: cy
            if (listOf(a, b, c, alpha, beta, gamma, area, height, ax, ay, bx, by, cx, cy).filter { it == null }.count() == 0) break
        }
    }
}

private fun Double?.isValidAngle(): Boolean = this == null || (this > 0.0 && this < 180)

private fun calcAngle(
    leftAngle: Double?,
    rightAngle: Double?,
    oppositeSide: Double?,
    leftSide: Double?,
    rightSide: Double?,
    area: Double?,
    height: Double?,  // Only applicable when calculating alpha
    thisPoint: Point,
    leftPoint: Point,
    rightPoint: Point
): Double? = when {
    leftAngle.isNum() && rightAngle.isNum() -> TOTAL_ANGLE_DEG - leftAngle - rightAngle
    leftSide.isPos() && rightSide.isPos() && oppositeSide.isNum() -> RAD_TO_DEG * acos((square(leftSide) + square(rightSide) - square(oppositeSide)) / (2.0 * leftSide * rightSide)) // c^2 = a^2 + b^2 - 2ab*cos(C)
    oppositeSide.isNum() && leftSide.isPos() && rightAngle.isNum() -> RAD_TO_DEG * asin(oppositeSide * sin(rightAngle * DEG_TO_RAD) / leftSide) // sin(A)/a = sin(B)/b = sin(C)/c
    oppositeSide.isNum() && rightSide.isPos() && leftAngle.isNum() -> RAD_TO_DEG * asin(oppositeSide * sin(leftAngle * DEG_TO_RAD) / rightSide) // sin(A)/a = sin(B)/b = sin(C)/c
    area.isNum() && leftSide.isPos() && rightSide.isPos() -> RAD_TO_DEG * asin(2.0 * area / leftSide / rightSide) // Area = 1/2 * ab * sin(C)
    else -> null
}

private fun calcSide(
    oppositeAngle: Double?,
    leftAngle: Double?,
    rightAngle: Double?,
    leftSide: Double?,
    rightSide: Double?,
    area: Double?,
    height: Double?, // Only applicable when calculating side a
    oppositePoint: Point,
    leftPoint: Point,
    rightPoint: Point
): Double? = when {
    oppositeAngle.isNum() && leftSide.isNum() && rightSide.isNum() ->
        sqrt(square(leftSide) + square(rightSide) - 2.0 * leftSide * rightSide * cos(DEG_TO_RAD * oppositeAngle)) // c^2 = a^2 + b^2 - 2ab*cos(C)
    oppositeAngle.isNum() && leftSide.isNum() && rightAngle.isNum() -> sin(DEG_TO_RAD * oppositeAngle) * leftSide / sin(DEG_TO_RAD * rightAngle) // sin(A)/a = sin(B)/b = sin(C)/c
    oppositeAngle.isNum() && rightSide.isNum() && leftAngle.isNum() -> sin(DEG_TO_RAD * oppositeAngle) * rightSide / sin(DEG_TO_RAD * leftAngle) // sin(A)/a = sin(B)/b = sin(C)/c
    area.isNum() && leftAngle.isNum() && leftSide.isNum() -> 2.0 * area / leftSide / sin(DEG_TO_RAD * leftAngle) // Area = 1/2 * ab * sin(C)
    area.isNum() && rightAngle.isNum() && rightSide.isNum() -> 2.0 * area / rightSide / sin(DEG_TO_RAD * rightAngle) // Area = 1/2 * ab * sin(C)
    leftPoint.x.isNum() && leftPoint.y.isNum() && rightPoint.x.isNum() && rightPoint.y.isNum() ->
        sqrt(square(rightPoint.x - leftPoint.x) + square(rightPoint.y - leftPoint.y)) // c^2 = a^2 + b^2
    height.isPos() && area.isNum() -> 2.0 * area / height // area = 1/2 bh
    else -> null
}

private fun calcArea(
    a: Double?,
    b: Double?,
    c: Double?,
    alpha: Double?,
    beta: Double?,
    gamma: Double?,
    height: Double?,
    pointA: Point,
    pointB: Point,
    pointC: Point?
): Double? = when {
    height.isNum() && a.isNum() -> a * height / 2.0 // area = 1/2 bh
    a.isNum() && b.isNum() && gamma.isNum() -> a * b * sin(DEG_TO_RAD * gamma) / 2.0// Area = 1/2 * ab * sin(C)
    b.isNum() && c.isNum() && alpha.isNum() -> b * c * sin(DEG_TO_RAD * alpha) / 2.0// Area = 1/2 * ab * sin(C)
    c.isNum() && a.isNum() && beta.isNum() -> c * a * sin(DEG_TO_RAD * beta) / 2.0// Area = 1/2 * ab * sin(C)
    a.isNum() && b.isNum() && c.isNum() -> {
        // area = sqrt(s(s-a)(s-b)(s-c)) where s = (a+b+c)/2
        val s = (a + b + c) / 2.0
        sqrt(s * (s - a) * (s - b) * (s - c))
    }
    else -> null
}

private fun calcHeight(
    a: Double?,
    b: Double?,
    c: Double?,
    alpha: Double?,
    beta: Double?,
    gamma: Double?,
    area: Double?,
    pointA: Point,
    pointB: Point,
    pointC: Point
): Double? = when {
    //pointC.y.isNum() && pointB.y == pointC.y && pointA.y.isNum() -> pointA.y
    area.isNum() && a.isPos() -> 2.0 * area / a // area = 1/2 bh
    area.isNum() && b.isPos() -> 2.0 * area / b // area = 1/2 bh
    area.isNum() && c.isPos() -> 2.0 * area / c // area = 1/2 bh
    else -> null
}

private fun calcPoint(
    self: Point,
    left: Point,
    right: Point,
    oppositeSide: Double?,
    leftSide: Double?,
    rightSide: Double?,
    thisAngle: Double?,
    leftAngle: Double?,
    rightAngle: Double?,
    area: Double?,
    height: Double? // Only applicable when calculating point a
): Point? = when {
    right.x.isNum() && right.y.isNum() && left.x.isNum() && left.y.isNum() && height.isNum() && rightSide.isNum() -> {
        val oppositeVec = Vector(left.x - right.x, left.y - right.y).normal
        val heightVec = oppositeVec.orthogonal * height
        val oppositeVecToOrtho = oppositeVec * sqrt(square(rightSide) - square(height))
        (right.toVector() + oppositeVecToOrtho + heightVec).toPoint()
    }
    right.x.isNum() && right.y.isNum() && left.x.isNum() && left.y.isNum() && height.isNum() && leftSide.isNum() -> {
        val oppositeVec = Vector(left.x - right.x, left.y - right.y).normal
        val heightVec = oppositeVec.orthogonal * height
        val oppositeVecToOrtho = -oppositeVec * sqrt(square(leftSide) - square(height))
        (left.toVector() + oppositeVecToOrtho + heightVec).toPoint()
    }
    left.x.isNum() && left.y.isNum() && right.x.isNum() && right.y.isNum() && self.x.isNum() && rightAngle.isNum() -> {
        val sin = sin(DEG_TO_RAD * rightAngle)
        val cos = cos(DEG_TO_RAD * rightAngle)
        val leftX = self.x - right.x
        val oppX = left.x - right.x
        val oppY = left.y - right.y
        if (cos * oppX - sin * oppY != 0.0) Point(self.x, leftX * (sin * oppX + cos * oppY) / (cos * oppX - sin * oppY) + right.y)
        else null
    }
    left.x.isNum() && left.y.isNum() && right.x.isNum() && right.y.isNum() && self.y.isNum() && rightAngle.isNum() -> {
        val sin = sin(DEG_TO_RAD * rightAngle)
        val cos = cos(DEG_TO_RAD * rightAngle)
        val leftY = self.y - right.y
        val oppX = left.x - right.x
        val oppY = left.y - right.y
        if (sin * oppX + cos * oppY != 0.0) Point(leftY * (cos * oppX - sin * oppY) / (sin * oppX + cos * oppY) + right.x, self.y)
        else null
    }
    else -> null
}

private fun square(value: Double): Double = value * value

private data class Point(val x: Double?, val y: Double?)
private data class Vector(val x: Double, val y: Double)

private fun Point.toVector(): Vector = Vector(x ?: 0.0, y ?: 0.0)
private fun Vector.toPoint(): Point = Point(x, y)

private val Vector.length get() = sqrt(x * x + y * y)
private val Vector.normal get() = this / length
private val Vector.orthogonal get() = Vector(-y, x)
private operator fun Vector.times(value: Double): Vector = Vector(x * value, y * value)
private operator fun Vector.div(value: Double): Vector = Vector(x / value, y / value)
private operator fun Vector.plus(other: Vector): Vector = Vector(x + other.x, y + other.y)
private operator fun Vector.unaryMinus(): Vector = Vector(-x, -y)

private fun Double?.isNum(): Boolean {
    contract { returns(true) implies (this@isNum != null) }
    return this != null
}

private fun Double?.isPos(): Boolean {
    contract { returns(true) implies (this@isPos != null) }
    return this.isNum() && this > 0.0
}
