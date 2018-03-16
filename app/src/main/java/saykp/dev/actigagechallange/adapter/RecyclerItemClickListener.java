package saykp.dev.actigagechallange.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;
    private OnDoubleClickListener mDoubleClick;
    private OnItemLongClickListenetr onItemLongClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    public interface OnDoubleClickListener{
        public void onDoubleClick(View view, int position);
    }
    public interface OnItemLongClickListenetr{
        public void onLongClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                return true;
            }


        });

        }

    public RecyclerItemClickListener(Context context,OnDoubleClickListener listener) {
        mDoubleClick = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {


            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {


                return true;
            }

        });



    }
    public RecyclerItemClickListener(Context context, OnItemLongClickListenetr listener) {
        onItemLongClickListener = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {


            @Override
            public void onLongPress(MotionEvent e) {
                Log.v("LongPress","LongPress");
                super.onLongPress(e);

            }
        });
    }

        @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
//        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
//            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
//        }
            if (childView != null && mDoubleClick != null && mGestureDetector.onTouchEvent(e)) {
                mDoubleClick.onDoubleClick(childView, view.getChildAdapterPosition(childView));
            }if (childView != null && onItemLongClickListener != null && mGestureDetector.onTouchEvent(e)){
                onItemLongClickListener.onLongClick(childView,view.getChildAdapterPosition(childView));
            }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}