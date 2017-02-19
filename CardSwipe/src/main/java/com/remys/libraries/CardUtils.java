package com.remys.libraries;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class CardUtils {
    public final static int DIRECTION_TOP = 0;
    public final static int DIRECTION_LEFT = 1;
    public final static int DIRECTION_RIGHT = 2;
    public final static int DIRECTION_BOTTOM = 3;

    public static void scale(View v, int pixel) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
        params.leftMargin -= pixel;
        params.rightMargin -= pixel;
        params.topMargin -= pixel;
        params.bottomMargin -= pixel;
        v.setLayoutParams(params);
    }

    public static LayoutParams getMoveParams(View v, int upDown, int leftRight) {
        RelativeLayout.LayoutParams original = (RelativeLayout.LayoutParams) v.getLayoutParams();
        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(original);
        RelativeLayout.LayoutParams params = cloneParams(original);
        params.leftMargin += leftRight;
        params.rightMargin -= leftRight;
        params.topMargin -= upDown;
        params.bottomMargin += upDown;
        return params;
    }

    public static void move(View v, int upDown, int leftRight) {
        RelativeLayout.LayoutParams params = getMoveParams(v, upDown, leftRight);
        v.setLayoutParams(params);
    }

    public static LayoutParams scaleFrom(View v, LayoutParams params, int pixel) {
        params = cloneParams(params);
        params.leftMargin -= pixel;
        params.rightMargin -= pixel;
        params.topMargin -= pixel;
        params.bottomMargin -= pixel;
        v.setLayoutParams(params);

        return params;
    }

    public static LayoutParams moveFrom(View v, LayoutParams params, int leftRight, int upDown) {
        params = cloneParams(params);
        params.leftMargin += leftRight;
        params.rightMargin -= leftRight;
        params.topMargin -= upDown;
        params.bottomMargin += upDown;
        v.setLayoutParams(params);

        return params;
    }

    //a copy method for RelativeLayout.LayoutParams for backward compartibility
    public static RelativeLayout.LayoutParams cloneParams(RelativeLayout.LayoutParams params) {
        RelativeLayout.LayoutParams copy = new RelativeLayout.LayoutParams(params.width, params.height);
        copy.leftMargin = params.leftMargin;
        copy.topMargin = params.topMargin;
        copy.rightMargin = params.rightMargin;
        copy.bottomMargin = params.bottomMargin;
        int[] rules = params.getRules();
        for (int i = 0; i < rules.length; i++) {
            copy.addRule(i, rules[i]);
        }
        //copy.setMarginStart(params.getMarginStart());
        //copy.setMarginEnd(params.getMarginEnd());

        return copy;
    }

    public static float distance(float x1, float y1, float x2, float y2) {

        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static int direction(float x1, float y1, float x2, float y2) {
        switch (getDirection(x1, y1, x2, y2)) {
            case DIRECTION_TOP:
                return DIRECTION_TOP;
            case DIRECTION_RIGHT:
                return DIRECTION_RIGHT;
            case DIRECTION_LEFT:
                return DIRECTION_LEFT;
            case DIRECTION_BOTTOM:
                return DIRECTION_BOTTOM;
        }

//        if (x2 > x1) {//RIGHT
//            if (y2 > y1) {//BOTTOM
//                return DIRECTION_BOTTOM_RIGHT;
//            } else {//TOP
//                return DIRECTION_TOP_RIGHT;
//            }
//        } else {//LEFT
//            if (y2 > y1) {//BOTTOM
//                return DIRECTION_BOTTOM_LEFT;
//            } else {//TOP
//                return DIRECTION_TOP_LEFT;
//            }
//        }
        return DIRECTION_BOTTOM;
    }


    private static Direction getDirection(float x1, float y1, float x2, float y2) {
        double angle = getAngle(x1, y1, x2, y2);
        return Direction.get(angle);
    }

    private static double getAngle(float x1, float y1, float x2, float y2) {

        double rad = Math.atan2(y1 - y2, x2 - x1) + Math.PI;
        return (rad * 180 / Math.PI + 180) % 360;
    }

    public enum Direction {
        DIRECTION_TOP,
        DIRECTION_LEFT,
        DIRECTION_RIGHT,
        DIRECTION_BOTTOM;
//        up,
//        down,
//        left,
//        right;

        public static Direction get(double angle) {
            if (inRange(angle, 45, 135)) {
                return Direction.DIRECTION_TOP;
            } else if (inRange(angle, 0, 45) || inRange(angle, 315, 360)) {
                return Direction.DIRECTION_RIGHT;
            } else if (inRange(angle, 225, 315)) {
                return Direction.DIRECTION_BOTTOM;
            } else {
                return Direction.DIRECTION_LEFT;
            }
        }


        private static boolean inRange(double angle, float init, float end) {
            return (angle >= init) && (angle < end);
        }
    }


}
