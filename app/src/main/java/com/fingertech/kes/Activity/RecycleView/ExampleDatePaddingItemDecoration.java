package com.fingertech.kes.Activity.RecycleView;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dant.centersnapreyclerview.SnappingRecyclerView;

public class ExampleDatePaddingItemDecoration extends RecyclerView.ItemDecoration {

    private @SnappingRecyclerView.OrientationMode int orientation;

    public ExampleDatePaddingItemDecoration(@SnappingRecyclerView.OrientationMode int orientation) {
        this.orientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(parent.getChildAdapterPosition(view) == 0) {
            if(orientation == SnappingRecyclerView.VERTICAL) {
                outRect.top = (parent.getHeight() / 2) - (view.getHeight() / 2);
            } else {
                outRect.left = (parent.getWidth() / 2) - (view.getWidth() / 2);
            }
        } else if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
            if(orientation == SnappingRecyclerView.VERTICAL) {
                outRect.bottom = (parent.getHeight() / 2) - (view.getHeight() / 2);
            } else {
                outRect.right = (parent.getWidth() / 2) - (view.getWidth() / 2);
            }
        }
    }
}