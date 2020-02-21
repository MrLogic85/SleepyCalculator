package com.sleepyduck.sleepycalculator.ui.geometry

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sleepyduck.geometry.Triangle
import com.sleepyduck.sleepycalculator.utils.MuteLiveData

class GeometryViewModel : ViewModel() {

    private val _a = MuteLiveData<Double>()
    private val _b = MuteLiveData<Double>()
    private val _c = MuteLiveData<Double>()
    private val _alpha = MuteLiveData<Double>()
    private val _beta = MuteLiveData<Double>()
    private val _gamma = MuteLiveData<Double>()
    private val _area = MuteLiveData<Double>()
    private val _height = MuteLiveData<Double>()
    private val _ax = MuteLiveData<Double>()
    private val _ay = MuteLiveData<Double>()
    private val _bx = MuteLiveData<Double>()
    private val _by = MuteLiveData(0.toDouble())
    private val _cx = MuteLiveData(0.toDouble())
    private val _cy = MuteLiveData(0.toDouble())

    private val _calculatedA = MuteLiveData<Double>()
    private val _calculatedB = MuteLiveData<Double>()
    private val _calculatedC = MuteLiveData<Double>()
    private val _calculatedAlpha = MuteLiveData<Double>()
    private val _calculatedBeta = MuteLiveData<Double>()
    private val _calculatedGamma = MuteLiveData<Double>()
    private val _calculatedArea = MuteLiveData<Double>()
    private val _calculatedHeight = MuteLiveData<Double>()
    private val _calculatedAx = MuteLiveData<Double>()
    private val _calculatedAy = MuteLiveData<Double>()
    private val _calculatedBx = MuteLiveData<Double>()
    private val _calculatedBy = MuteLiveData<Double>()
    private val _calculatedCx = MuteLiveData<Double>()
    private val _calculatedCy = MuteLiveData<Double>()

    val a: LiveData<Double> = _a
    val b: LiveData<Double> = _b
    val c: LiveData<Double> = _c
    val alpha: LiveData<Double> = _alpha
    val beta: LiveData<Double> = _beta
    val gamma: LiveData<Double> = _gamma
    val area: LiveData<Double> = _area
    val height: LiveData<Double> = _height
    val ax: LiveData<Double> = _ax
    val ay: LiveData<Double> = _ay
    val bx: LiveData<Double> = _bx
    val by: LiveData<Double> = _by
    val cx: LiveData<Double> = _cx
    val cy: LiveData<Double> = _cy

    val calculatedA: LiveData<Double> = _calculatedA
    val calculatedB: LiveData<Double> = _calculatedB
    val calculatedC: LiveData<Double> = _calculatedC
    val calculatedAlpha: LiveData<Double> = _calculatedAlpha
    val calculatedBeta: LiveData<Double> = _calculatedBeta
    val calculatedGamma: LiveData<Double> = _calculatedGamma
    val calculatedArea: LiveData<Double> = _calculatedArea
    val calculatedHeight: LiveData<Double> = _calculatedHeight
    val calculatedAx: LiveData<Double> = _calculatedAx
    val calculatedAy: LiveData<Double> = _calculatedAy
    val calculatedBx: LiveData<Double> = _calculatedBx
    val calculatedBy: LiveData<Double> = _calculatedBy
    val calculatedCx: LiveData<Double> = _calculatedCx
    val calculatedCy: LiveData<Double> = _calculatedCy

    fun setA(a: Double?): Boolean {
        _a.value = a
        return calculate()
    }

    fun setB(b: Double?): Boolean {
        _b.value = b
        return calculate()
    }

    fun setC(c: Double?): Boolean {
        _c.value = c
        return calculate()
    }

    fun setAlpha(alpha: Double?): Boolean {
        _alpha.value = alpha
        return calculate()
    }

    fun setBeta(beta: Double?): Boolean {
        _beta.value = beta
        return calculate()
    }

    fun setGamma(gamma: Double?): Boolean {
        _gamma.value = gamma
        return calculate()
    }

    fun setArea(area: Double?): Boolean {
        _area.value = area
        return calculate()
    }

    fun setHeight(height: Double?): Boolean {
        _height.value = height
        return calculate()
    }

    fun setAx(ax: Double?): Boolean {
        _ax.value = ax
        return calculate()
    }

    fun setAy(ay: Double?): Boolean {
        _ay.value = ay
        return calculate()
    }

    fun setBx(bx: Double?): Boolean {
        _bx.value = bx
        return calculate()
    }

    fun setBy(by: Double?): Boolean {
        _by.value = by
        return calculate()
    }

    fun setCx(cx: Double?): Boolean {
        _cx.value = cx
        return calculate()
    }

    fun setCy(cy: Double?): Boolean {
        _cy.value = cy
        return calculate()
    }

    private fun calculate(): Boolean {
        val triangle = Triangle(
            a.value,
            b.value,
            c.value,
            alpha.value,
            beta.value,
            gamma.value,
            area.value,
            height.value,
            ax.value,
            ay.value,
            bx.value,
            by.value,
            cx.value,
            cy.value
        )

        if (triangle.isValid) {
            _calculatedA.value = triangle.a
            _calculatedB.value = triangle.b
            _calculatedC.value = triangle.c
            _calculatedAlpha.value = triangle.alpha
            _calculatedBeta.value = triangle.beta
            _calculatedGamma.value = triangle.gamma
            _calculatedArea.value = triangle.area
            _calculatedHeight.value = triangle.height
            _calculatedAx.value = triangle.ax
            _calculatedAy.value = triangle.ay
            _calculatedBx.value = triangle.bx
            _calculatedBy.value = triangle.by
            _calculatedCx.value = triangle.cx
            _calculatedCy.value = triangle.cy
        }

        return triangle.isValid
    }
}
