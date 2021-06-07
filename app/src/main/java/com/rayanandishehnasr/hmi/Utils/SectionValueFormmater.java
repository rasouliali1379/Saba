package com.rayanandishehnasr.hmi.Utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class SectionValueFormmater extends ValueFormatter
{

    private String suffix;

    public SectionValueFormmater(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String getFormattedValue(float value) {
        return (int) value + suffix;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        if (axis instanceof XAxis) {
            return suffix + (int) value;
        } else if (value > 0) {
            return suffix + (int) value;
        } else {
            return suffix + (int) value;
        }
    }
}