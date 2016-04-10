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
 * Created by Silver on 6.04.2016.
 */
public class CalculationTypesAdapter extends CursorAdapter {

    private final LayoutInflater layoutInflater;
    private UOW uow;
    private ViewGroup parentViewGroup;

    public CalculationTypesAdapter(Context context, Cursor c, UOW uow) {
        super(context, c, 0);
        layoutInflater = LayoutInflater.from(context);
        this.uow = uow;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = layoutInflater.inflate(R.layout.calc_type, parent, false);
        parentViewGroup = parent;
        return view;
    }


    // this can be called several times by the system!!!
    // first pass - initial draw, get measurements
    // second pass - final draw
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //TextView textViewName = (TextView) view.findViewById(R.id.typeName);
        // vb nii teha et on + ja siis all kellaajad mil kasutatud?
        CalculationType calculationType = uow.typesRepo.cursorToEntity(cursor);
        //textViewName.setText(calculationType.getOperandsCount());
        displayTypeView(view, context, calculationType);
    }


    private void displayTypeView(View view, Context context, CalculationType calculationType) {
        // get the contactsListView
        LinearLayout typesListView = (LinearLayout) view.findViewById(R.id.typesListView);

        // if this gets called multiple times, first clean all up
        // otherwise you will add same childs several times
        typesListView.removeAllViews();

        for (CalculationType type :
                uow.typesRepo.getTypeList(calculationType.getId())) {

            // load the xml structure of your row
            View child = layoutInflater.inflate(R.layout.calc_type_details, parentViewGroup, false);

            TextView textViewOperandType = (TextView) child.findViewById(R.id.operandType);
            TextView textViewOperandCount = (TextView) child.findViewById(R.id.typeLifetime);

            textViewOperandType.setText(type.getOperand());
            textViewOperandCount.setText(String.format("%s", type.getLifetimeCounter()));

            typesListView.addView(child);
        }


    }


}
