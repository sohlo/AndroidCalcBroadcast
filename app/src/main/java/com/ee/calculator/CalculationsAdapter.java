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
public class CalculationsAdapter extends CursorAdapter {

    private final LayoutInflater layoutInflater;
    private UOW uow;
    private ViewGroup parentViewGroup;

    public CalculationsAdapter(Context context, Cursor c, UOW uow) {
        super(context, c, 0);
        layoutInflater = LayoutInflater.from(context);
        this.uow = uow;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = layoutInflater.inflate(R.layout.calculations, parent, false);
        parentViewGroup = parent;
        return view;
    }


    // this can be called several times by the system!!!
    // first pass - initial draw, get measurements
    // second pass - final draw
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //TextView textViewName = (TextView) view.findViewById(R.id.calcName);

        Calculations calculations = uow.calculationsRepo.cursorToEntity(cursor);

        displayTypeView(view, context, calculations);
    }


    private void displayTypeView(View view, Context context, Calculations calculations) {
        // get the calcsListView
        LinearLayout calcListView = (LinearLayout) view.findViewById(R.id.calcsListView);

        // if this gets called multiple times, first clean all up
        // otherwise you will add same childs several times
        calcListView.removeAllViews();

        for (Calculations calc :
                uow.calculationsRepo.getCalcList(calculations.getId())) {

            // load the xml structure of your row
            View child = layoutInflater.inflate(R.layout.calculations_details, parentViewGroup, false);

            TextView textViewOperation = (TextView) child.findViewById(R.id.calcOperation);
            TextView textViewCalcRes = (TextView) child.findViewById(R.id.calcResult);
            TextView textViewCalcTimestamp = (TextView) child.findViewById(R.id.calcTimestamp);

            uow.calculationTypesRepo.getOperandById(calculations.getOperandId());
            String operand = uow.calculationTypesRepo.getById(calculations.getOperandId()).getOperand();

            textViewOperation.setText(String.format("%s%s%s", calculations.getNum1(), operand, calculations.getNum2()));
            textViewCalcRes.setText(String.format("%s", calc.getRes()));
            textViewCalcTimestamp.setText(String.format("%s", calc.getTimestamp()));

            calcListView.addView(child);

        }


    }
}
