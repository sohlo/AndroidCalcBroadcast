package com.ee.calculator;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Silver on 7.04.2016.
 */
public class CalculationStatsAdapter extends CursorAdapter {

    private final LayoutInflater layoutInflater;
    private UOW uow;
    private ViewGroup parentViewGroup;

    public CalculationStatsAdapter(Context context, Cursor c, UOW uow) {
        super(context, c, 0);
        layoutInflater = LayoutInflater.from(context);
        this.uow = uow;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = layoutInflater.inflate(R.layout.calculation_stats, parent, false);
        parentViewGroup = parent;
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CalculationStats calculations = uow.calculationStatsRepo.cursorToEntity(cursor);
        displayTypeView(view, context, calculations);
    }

    private void displayTypeView(View view, Context context, CalculationStats calculationStats) {
        // get the calcsListView
        LinearLayout calcListView = (LinearLayout) view.findViewById(R.id.statsListView);

        // if this gets called multiple times, first clean all up
        // otherwise you will add same childs several times
        calcListView.removeAllViews();

        for (CalculationStats stats :
                uow.calculationStatsRepo.getStatList(calculationStats.getId())) {

            // load the xml structure of your row
            View child = layoutInflater.inflate(R.layout.calculation_stats_details, parentViewGroup, false);

            TextView textViewOperand = (TextView) child.findViewById(R.id.operandsType);
            TextView textViewStatCounter = (TextView) child.findViewById(R.id.statCounter);
            String operand = uow.calculationTypesRepo.getById(stats.getOperandId()).getOperand();
            textViewOperand.setText(String.format("%s", operand));
            textViewStatCounter.setText(String.format("%s", stats.getDayCounter()));

            calcListView.addView(child);
        }


    }

}
