package com.sleepyduck.sleepycalculator.ui.geometry

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.TextViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.sleepyduck.sleepycalculator.R
import kotlinx.android.synthetic.main.fragment_geometry.view.*
import kotlin.math.round


class GeometryFragment : Fragment() {

    private val geometryViewModel: GeometryViewModel by lazy { ViewModelProvider(this)[GeometryViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_geometry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.input_a.setText(geometryViewModel.a.value)
        view.input_b.setText(geometryViewModel.b.value)
        view.input_c.setText(geometryViewModel.c.value)
        view.input_alpha.setText(geometryViewModel.alpha.value)
        view.input_beta.setText(geometryViewModel.beta.value)
        view.input_gamma.setText(geometryViewModel.gamma.value)
        view.input_area.setText(geometryViewModel.area.value)
        view.input_height.setText(geometryViewModel.height.value)
        view.input_ax.setText(geometryViewModel.ax.value)
        view.input_ay.setText(geometryViewModel.ay.value)
        view.input_bx.setText(geometryViewModel.bx.value)
        view.input_by.setText(geometryViewModel.by.value)
        view.input_cx.setText(geometryViewModel.cx.value)
        view.input_cy.setText(geometryViewModel.cy.value)
        geometryViewModel.calculatedA.observe(viewLifecycleOwner::getLifecycle) { view.input_a_layout.setCalcValue(it) }
        geometryViewModel.calculatedB.observe(viewLifecycleOwner::getLifecycle) { view.input_b_layout.setCalcValue(it) }
        geometryViewModel.calculatedC.observe(viewLifecycleOwner::getLifecycle) { view.input_c_layout.setCalcValue(it) }
        geometryViewModel.calculatedAlpha.observe(viewLifecycleOwner::getLifecycle) { view.input_alpha_layout.setCalcValue(it) }
        geometryViewModel.calculatedBeta.observe(viewLifecycleOwner::getLifecycle) { view.input_beta_layout.setCalcValue(it) }
        geometryViewModel.calculatedGamma.observe(viewLifecycleOwner::getLifecycle) { view.input_gamma_layout.setCalcValue(it) }
        geometryViewModel.calculatedArea.observe(viewLifecycleOwner::getLifecycle) { view.input_area_layout.setCalcValue(it) }
        geometryViewModel.calculatedHeight.observe(viewLifecycleOwner::getLifecycle) { view.input_height_layout.setCalcValue(it) }
        geometryViewModel.calculatedAx.observe(viewLifecycleOwner::getLifecycle) { view.input_ax_layout.setCalcValue(it) }
        geometryViewModel.calculatedAy.observe(viewLifecycleOwner::getLifecycle) { view.input_ay_layout.setCalcValue(it) }
        geometryViewModel.calculatedBx.observe(viewLifecycleOwner::getLifecycle) { view.input_bx_layout.setCalcValue(it) }
        geometryViewModel.calculatedBy.observe(viewLifecycleOwner::getLifecycle) { view.input_by_layout.setCalcValue(it) }
        geometryViewModel.calculatedCx.observe(viewLifecycleOwner::getLifecycle) { view.input_cx_layout.setCalcValue(it) }
        geometryViewModel.calculatedCy.observe(viewLifecycleOwner::getLifecycle) { view.input_cy_layout.setCalcValue(it) }
        view.input_a.addTextChangedListener { view.input_a_layout.applyValue(it.toDouble(), geometryViewModel::setA) }
        view.input_b.addTextChangedListener { view.input_b_layout.applyValue(it.toDouble(), geometryViewModel::setB) }
        view.input_c.addTextChangedListener { view.input_c_layout.applyValue(it.toDouble(), geometryViewModel::setC) }
        view.input_alpha.addTextChangedListener { view.input_alpha_layout.applyValue(it.toDouble(), geometryViewModel::setAlpha) }
        view.input_beta.addTextChangedListener { view.input_beta_layout.applyValue(it.toDouble(), geometryViewModel::setBeta) }
        view.input_gamma.addTextChangedListener { view.input_gamma_layout.applyValue(it.toDouble(), geometryViewModel::setGamma) }
        view.input_area.addTextChangedListener { view.input_area_layout.applyValue(it.toDouble(), geometryViewModel::setArea) }
        view.input_height.addTextChangedListener { view.input_height_layout.applyValue(it.toDouble(), geometryViewModel::setHeight) }
        view.input_ax.addTextChangedListener { view.input_ax_layout.applyValue(it.toDouble(), geometryViewModel::setAx) }
        view.input_ay.addTextChangedListener { view.input_ay_layout.applyValue(it.toDouble(), geometryViewModel::setAy) }
        view.input_bx.addTextChangedListener { view.input_bx_layout.applyValue(it.toDouble(), geometryViewModel::setBx) }
        view.input_by.addTextChangedListener { view.input_by_layout.applyValue(it.toDouble(), geometryViewModel::setBy) }
        view.input_cx.addTextChangedListener { view.input_cx_layout.applyValue(it.toDouble(), geometryViewModel::setCx) }
        view.input_cy.addTextChangedListener { view.input_cy_layout.applyValue(it.toDouble(), geometryViewModel::setCy) }
    }
}

private const val TAG_CALCULATED = "calculated"

private fun TextInputLayout.setCalcValue(value: Double?) {
    when {
        (editText?.text.isNullOrBlank() || tag == TAG_CALCULATED) && value != null -> {
            editText?.isEnabled = false
            tag = TAG_CALCULATED
            isErrorEnabled = false
            editText?.let { TextViewCompat.setTextAppearance(it, R.style.TextAppearanceCalculated) }
            editText?.setText(value.trim())
        }

        editText?.text?.isNotEmpty() == true && tag != TAG_CALCULATED && value != null && value.trim() != editText?.text.toDouble()?.trim() -> {
            error = value.trim()
        }

        editText?.text?.isNotEmpty() == true && tag != TAG_CALCULATED && value != null -> {
            isErrorEnabled = false
        }

        value == null && tag == TAG_CALCULATED -> {
            editText?.text = null
            tag = null
            editText?.let { TextViewCompat.setTextAppearance(it, R.style.TextAppearance) }
            editText?.isEnabled = true
        }

        value == null -> {
            isErrorEnabled = false
        }
    }
}

private fun TextInputLayout.applyValue(value: Double?, apply: (Double?) -> Boolean) {
    when {
        tag != TAG_CALCULATED -> if (!apply(value) && value != null) {
            error = "Invalid value"
        } else if (error == "Invalid value") {
            isErrorEnabled = false
        }
    }
}

private fun AppCompatEditText.setText(value: Double?) = setText(if (value != null) "$value" else "")

private fun Double.trim(): String = "${round(this * 10000.0) / 10000.0}"

private fun Editable?.toDouble(): Double? = try {
    this.toString().toDouble()
} catch (e: NumberFormatException) {
    null
}
