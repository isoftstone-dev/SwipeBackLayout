package com.isoftstone.swipelayout;


import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.DragInfo;
import ohos.agp.components.PositionLayout;
import ohos.agp.utils.Point;
import ohos.app.Context;
import ohos.multimodalinput.event.TouchEvent;

import java.util.LinkedHashMap;


/**
 * 提供侧滑删除等滑动方向的视图展示效果
 *
 * @author lemon0101
 * @version 1.0
 * @since 2/7
 */
public class SwipeLayout extends PositionLayout {
    // private static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x000110, "SwipeLayout");
    /**
     * 控件当前的开启状态
     */
    private Status myStatus;
    /**
     * 拖拽事件的开始位置
     */
    private Point myDragStartPoint;
    /**
     * 拖拽事件的结束位置
     */
    private Point myDragEndPoint;
    /**
     * 首次滑动方向作为此次滑动的方向
     */
    private DragEdge myCurrentDragEdge;
    //    private boolean mClickToClose = false;
    //    private float mWillOpenPercentAfterOpen = 0.75f;
    /**
     * 拖动距离超过对应视图百分比时自动打开
     */
    private float myWillOpenPercentAfterClose = 0.25f;
    //    private static final DragEdge DefaultDragEdge = DragEdge.Right;
    /**
     * 拖动方向与视图的对应
     */
    private LinkedHashMap<DragEdge, Component> myDragEdges = new LinkedHashMap<>();
    /**
     * 拖动前是否为打开状态
     */
    private boolean isCloseBeforeDrag = true;

    /**
     * 构造函数
     *
     * @param context context
     */
    public SwipeLayout(Context context) {
        super(context);
    }

    /**
     * 构造函数
     *
     * @param context context
     * @param attrs   attrs
     */
    public SwipeLayout(Context context, AttrSet attrs) {
        this(context, attrs, null);
    }

    /**
     * 构造函数
     *
     * @param context   context
     * @param attrs     attrs
     * @param styleName styleName
     */
    public SwipeLayout(Context context, AttrSet attrs, String styleName) {
        super(context, attrs, styleName);

        setDraggedListener(DRAG_HORIZONTAL, new DraggedListener() {
            /**
             * 按下
             */
            @Override
            public void onDragDown(Component component, DragInfo dragInfo) {
                //  HiLog.info(label, "Log_onDragDown" + "downPoint:" + dragInfo.downPoint);
                isCloseBeforeDrag = myStatus == Status.Close;
            }

            /**
             * 开始拖拽
             */
            @Override
            public void onDragStart(Component component, DragInfo dragInfo) {
                myDragStartPoint = dragInfo.startPoint;
            }

            /**
             * 拖拽信息更新
             */
            @Override
            public void onDragUpdate(Component component, DragInfo dragInfo) {
                //根据第一次拖拽信息的更新来决定本次拖拽收视以哪种方向被响应，直到再次成为关闭状态作为一次操作完成。
                if (myCurrentDragEdge == null) {
                    setCurrentDragEdge(getCurrentDragEdgeByDragInfo(dragInfo));
                    // myCurrentDragEdge = getCurrentDragEdgeByDragInfo(dragInfo);
                }
                //没有对应视图则不响应拖拽
                if (getCurrentBottomView() == null) {
                    return;
                }
                //    HiLog.info(label, "Log_onDragUpdate" + "speed:" + dragInfo.xVelocity);
                if (myCurrentDragEdge == DragEdge.Left) {
                    if (getSurfaceView().getContentPositionX() + dragInfo.xOffset >= 0) {
                        close();
                    } else if (getSurfaceView().getContentPositionX() + dragInfo.xOffset <= -getBottomViewWidth()) {
                        open();
                    } else {
                        getSurfaceView().setContentPositionX(getSurfaceView().getContentPositionX() + (float) dragInfo.xOffset);
                        getCurrentBottomView().setContentPositionX(getCurrentBottomView().getContentPositionX() + (float) dragInfo.xOffset);
                    }
                }
                if (myCurrentDragEdge == DragEdge.Right) {
                    if (getSurfaceView().getContentPositionX() + dragInfo.xOffset <= 0) {
                        close();
                    } else if (getSurfaceView().getContentPositionX() + dragInfo.xOffset >= getBottomViewWidth()) {
                        open();
                    } else {
                        getSurfaceView().setContentPositionX(getSurfaceView().getContentPositionX() + (float) dragInfo.xOffset);
                        getCurrentBottomView().setContentPositionX(getCurrentBottomView().getContentPositionX() + (float) dragInfo.xOffset);
                    }
                }
                if (myCurrentDragEdge == DragEdge.Top) {
                    if (getSurfaceView().getContentPositionY() + dragInfo.yOffset >= 0) {
                        close();
                    } else if (getSurfaceView().getContentPositionY() + dragInfo.yOffset <= -getHeight()) {
                        open();
                    } else {
                        getSurfaceView().setContentPositionY(getSurfaceView().getContentPositionY() + (float) dragInfo.yOffset);
                        getCurrentBottomView().setContentPositionY(getCurrentBottomView().getContentPositionY() + (float) dragInfo.yOffset);
                    }
                }
                if (myCurrentDragEdge == DragEdge.Bottom) {
                    if (getSurfaceView().getContentPositionY() + dragInfo.yOffset <= 0) {
                        close();
                    } else if (getSurfaceView().getContentPositionY() + dragInfo.yOffset >= getHeight()) {
                        open();
                    } else {
                        getSurfaceView().setContentPositionY(getSurfaceView().getContentPositionY() + (float) dragInfo.yOffset);
                        getCurrentBottomView().setContentPositionY(getCurrentBottomView().getContentPositionY() + (float) dragInfo.yOffset);
                    }
                }
            }

            /**
             * 拖拽结束
             */
            @Override
            public void onDragEnd(Component component, DragInfo dragInfo) {
                if (getCurrentBottomView() == null) {
                    return;
                }
                myDragEndPoint = dragInfo.updatePoint;
                int mDragDistanceX = myDragEndPoint.getPointXToInt() - myDragStartPoint.getPointXToInt();
                int mDragDistanceY = myDragEndPoint.getPointYToInt() - myDragStartPoint.getPointYToInt();
                // HiLog.info(label, "Log_onDragEnd" + "mDragDistanceY:" + mDragDistanceY);
                if (myCurrentDragEdge == DragEdge.Left) {
                    if (isCloseBeforeDrag && mDragDistanceX < 0) {
                        if (Math.abs(mDragDistanceX) >= myWillOpenPercentAfterClose * getBottomViewWidth()) {
                            open();
                        } else {
                            close();
                        }
                    }
                    if (!isCloseBeforeDrag && mDragDistanceX > 0) {
                        if (Math.abs(mDragDistanceX) >= myWillOpenPercentAfterClose * getBottomViewWidth()) {
                            close();
                        } else {
                            open();
                        }
                    }
                }
                if (myCurrentDragEdge == DragEdge.Right) {
                    if (isCloseBeforeDrag && mDragDistanceX > 0) {
                        if (Math.abs(mDragDistanceX) >= myWillOpenPercentAfterClose * getBottomViewWidth()) {
                            open();
                        } else {
                            close();
                        }
                    }
                    if (!isCloseBeforeDrag && mDragDistanceX < 0) {
                        if (Math.abs(mDragDistanceX) >= myWillOpenPercentAfterClose * getBottomViewWidth()) {
                            close();
                        } else {
                            open();
                        }
                    }
                }
                if (myCurrentDragEdge == DragEdge.Bottom) {
                    if (isCloseBeforeDrag && mDragDistanceY > 0) {
                        if (Math.abs(mDragDistanceY) >= myWillOpenPercentAfterClose * getBottomViewHeight()) {
                            open();
                        } else {
                            close();
                        }
                    }
                    if (!isCloseBeforeDrag && mDragDistanceY < 0) {
                        if (Math.abs(mDragDistanceY) >= myWillOpenPercentAfterClose * getBottomViewHeight()) {
                            close();
                        } else {
                            open();
                        }
                    }
                }
                if (myCurrentDragEdge == DragEdge.Top) {
                    if (isCloseBeforeDrag && mDragDistanceY < 0) {
                        if (Math.abs(mDragDistanceY) >= myWillOpenPercentAfterClose * getBottomViewHeight()) {
                            open();
                        } else {
                            close();
                        }
                    }
                    if (!isCloseBeforeDrag && mDragDistanceY > 0) {
                        if (Math.abs(mDragDistanceY) >= myWillOpenPercentAfterClose * getBottomViewHeight()) {
                            close();
                        } else {
                            open();
                        }
                    }
                }

                if (myStatus == Status.Close) {
                    setCurrentDragEdge(null);
                    //    myCurrentDragEdge = null;
                }
            }

            /**
             * 拖拽取消
             */
            @Override
            public void onDragCancel(Component component, DragInfo dragInfo) {
                //  HiLog.info(label, "Log_onDragCancel" + "Tag:" + component.getTag());
            }

            /**
             * 是否响应
             */
            @Override
            public boolean onDragPreAccept(Component component, int dragDirection) {
                //  HiLog.info(label, "Log_onDragPreAccept" + "dragDirection:" + dragDirection);
                return true;
            }
        });
        /**
         * touch监听
         */
        setTouchEventListener(new TouchEventListener() {
            @Override
            public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
                // HiLog.info(label, "Log_swipeTouch");
                if (touchEvent.getAction() == TouchEvent.POINT_MOVE) {
                    return false;
                }
                return false;
            }
        });

    }

//    @Override
//    public boolean onDrag(Component component, DragEvent event) {
//        HiLog.info(label, "Log_onDragBool");
//        return super.onDrag(component, event);
//    }

    /**
     * 初始化
     */
    public void initializeSwipe() {
        // myCurrentDragEdge = null;
        setCurrentDragEdge(null);
        myDragEdges.put(DragEdge.Left, null);
        myDragEdges.put(DragEdge.Right, null);
        myDragEdges.put(DragEdge.Top, null);
        myDragEdges.put(DragEdge.Bottom, null);
        for (int i = 0; i < getChildCount(); i++) {
            if (i == 0) {
                continue;
            }
            Component viewChild = getComponentAt(i);
            viewChild.setVisibility(INVISIBLE);
        }
        // HiLog.info(label, "Log_initializeSwipe" + "count" + getChildCount());
        //  moveChildToFront(getSurfaceView());
        //目前只支持右边
        //  HiLog.info(label, "Log_initializeSwipe" + "count" + getChildCount());
        // addDrag(DragEdge.Left, bottomView);
        //  getCurrentBottomView().setContentPosition(DeviceUtils.getscreenW(getContext()), 0);
        getSurfaceView().setContentPosition(0, 0);
//        // 创建背景元素
//        ShapeElement shapeElement = new ShapeElement();
//        // 设置颜色
//        shapeElement.setRgbColor(RgbColor.fromArgbInt(Color.GREEN.getValue()));
//        getSurfaceView().setBackground(shapeElement);
        myStatus = Status.Close;
    }


    @Override
    public boolean isTouchFocusable() {
        return super.isTouchFocusable();
    }

    @Override
    public void setTouchEventListener(TouchEventListener listener) {
        super.setTouchEventListener(listener);
    }

    /**
     * 添加滑动方向对应的底部视图
     *
     * @param dragEdge 滑动方向
     * @param child    对应的视图
     */
    public void addDrag(DragEdge dragEdge, Component child) {
        myDragEdges.put(dragEdge, child);
        switch (dragEdge) {
            case Left:
                child.setContentPosition(getWidth(), 0);
                break;
            case Right:
                //   HiLog.info(label, "Log_addDrag" + child.getHeight());
                child.setContentPosition(-child.getWidth(), 0);
                break;
            case Top:
                child.setContentPosition(0, getHeight());
                break;
            case Bottom:
                child.setContentPosition(0, -child.getHeight());
                break;
            default:
        }
        child.setVisibility(INVISIBLE);
        addComponent(child, 0);
    }

    @Override
    public void addComponent(Component childComponent, int index) {
        if (childComponent.getComponentParent() == this) {
            return;
        }
        super.addComponent(childComponent, index);
    }

    /**
     * 设置当前拖拽方向
     *
     * @param currentDragEdge currentDragEdge
     */
    private void setCurrentDragEdge(DragEdge currentDragEdge) {
        //   HiLog.info(label, "Log_setCurrentDragEdge" + currentDragEdge);
        if (myDragEdges.get(currentDragEdge) == null) {
            myCurrentDragEdge = null;
            return;
        }
        myCurrentDragEdge = currentDragEdge;
        switch (myCurrentDragEdge) {
            case Left:
                getCurrentBottomView().setContentPosition(getWidth(), 0);
                break;
            case Right:
                //    HiLog.info(label, "Log_addDrag" + getBottomViewWidth());
                getCurrentBottomView().setContentPosition(-getBottomViewWidth(), 0);
                break;
            case Top:
                getCurrentBottomView().setContentPosition(0, getHeight());
                break;
            case Bottom:
                getCurrentBottomView().setContentPosition(0, -getBottomViewHeight());
                break;
            default:
        }
        if (getCurrentBottomView().getVisibility() != VISIBLE) {
            getCurrentBottomView().setVisibility(VISIBLE);
        }

        //  updateBottomViews();
    }


//    private void updateBottomViews() {
//        Component currentBottomView = getCurrentBottomView();
//        currentBottomView.setVisibility(VISIBLE);
//        //  safeBottomView();
//    }

//    private void safeBottomView() {
//        Status status = getOpenStatus();
//        List<Component> bottoms = getBottomViews();
//        if (status == Status.Close) {
//            for (Component bottom : bottoms) {
//                if (bottom != null && bottom.getVisibility() != INVISIBLE) {
//                    bottom.setVisibility(INVISIBLE);
//                }
//            }
//        } else {
//            Component currentBottomView = getCurrentBottomView();
//            if (currentBottomView != null && currentBottomView.getVisibility() != VISIBLE) {
//                currentBottomView.setVisibility(VISIBLE);
//            }
//        }
//    }


//    private List<Component> getBottomViews() {
//        ArrayList<Component> bottoms = new ArrayList<Component>();
//        for (DragEdge dragEdge : DragEdge.values()) {
//            bottoms.add(myDragEdges.get(dragEdge));
//        }
//        return bottoms;
//    }
//
//    private void setTagForAllCompont() {
//        int size = this.getChildCount();
//        for (int i = 0; i < size; i++) {
//            getComponentAt(i).setTag(i);
//        }
//    }

    /**
     * 获取表层视图
     *
     * @return Component
     */
    private Component getSurfaceView() {
        // return getComponentAt(getChildCount() - 1);
        return getComponentAt(0);
    }

    /**
     * 获取当前展示的底层视图
     *
     * @return Component
     */
    private Component getCurrentBottomView() {
        //   HiLog.info(label, "Log_getCurrentBottomView" + myCurrentDragEdge);
        return myDragEdges.get(myCurrentDragEdge);
    }

    /**
     * 根据拖拽方向获取底层视图
     *
     * @param dragEdge dragEdge
     * @return Component
     */
    private Component getBottomView(DragEdge dragEdge) {
        return myDragEdges.get(dragEdge);
    }

    /**
     * 关闭侧滑
     */
    private void close() {
        switch (myCurrentDragEdge) {
            case Left:
                getSurfaceView().setContentPositionX(0);
                getCurrentBottomView().setContentPositionX(getWidth());
                break;
            case Right:
                getSurfaceView().setContentPositionX(0);
                getCurrentBottomView().setContentPositionX(-getBottomViewWidth());
                break;
            case Top:
                getSurfaceView().setContentPositionY(0);
                getCurrentBottomView().setContentPositionY(getHeight());
                break;
            case Bottom:
                getSurfaceView().setContentPositionY(0);
                getCurrentBottomView().setContentPositionY(-getHeight());
                break;
            default:
        }
        getCurrentBottomView().setVisibility(INVISIBLE);
        myStatus = Status.Close;
    }

    /**
     * 侧滑打开
     */
    private void open() {
        switch (myCurrentDragEdge) {
            case Left:
                getSurfaceView().setContentPositionX(-getBottomViewWidth());
                getCurrentBottomView().setContentPositionX(DeviceUtils.getscreenW(getContext()) - getBottomViewWidth());
                break;
            case Right:
                getSurfaceView().setContentPositionX(getBottomViewWidth());
                getCurrentBottomView().setContentPositionX(0);
                break;
            case Top:
                getSurfaceView().setContentPositionY(-getBottomViewHeight());
                getCurrentBottomView().setContentPositionY(0);
                break;
            case Bottom:
                getSurfaceView().setContentPositionY(getBottomViewHeight());
                getCurrentBottomView().setContentPositionY(0);
                break;
            default:
        }
        myStatus = Status.Open;
    }

    /**
     * 侧滑状态
     */
    public enum Status {
        /**
         * 中间
         */
        Middle,
        /**
         * 打开
         */
        Open,
        /**
         * 关闭
         */
        Close
    }

    /**
     * 拖拽方向
     */
    public enum DragEdge {
        /**
         * 左
         */
        Left,
        /*上*/
        Top,
        /*
        右
         */
        Right,
        //下
        Bottom
    }

    /**
     * 获取侧滑开关状态
     *
     * @return Status
     */
    public Status getOpenStatus() {
        //  HiLog.info(label, "Log_getOpenStatus" + getSurfaceView().getContentPositionX());
        return myStatus;
    }

    //判断方法不准确
//    public Status getOpenStatus() {
//        HiLog.info(label, "Log_getOpenStatus" + getSurfaceView().getContentPositionX());
//        if (getSurfaceView().getContentPositionX() == 0) {
//            return Status.Close;
//        } else if (getSurfaceView().getContentPositionX() == -getBottomViewWidth()) {
//            return Status.Open;
//        } else {
//            return Status.Middle;
//        }
//    }

    /**
     * 获取底部视图的宽度
     *
     * @return int
     */
    private int getBottomViewWidth() {
        return getCurrentBottomView().getWidth();
    }

    /**
     * 获取底部视图的高度
     *
     * @return int
     */
    private int getBottomViewHeight() {
        return getCurrentBottomView().getHeight();
    }

    /**
     * 根据拖拽信息获取拖拽方向
     *
     * @param dragInfo dragInfo
     * @return DragEdge DragEdge
     */
    private DragEdge getCurrentDragEdgeByDragInfo(DragInfo dragInfo) {
        float distanceX = (float) dragInfo.xOffset;
        float distanceY = (float) dragInfo.yOffset;
        float angle = Math.abs(distanceY / distanceX);
        angle = (float) Math.toDegrees(Math.atan(angle));
        if (angle < 45) {
            if (distanceX > 0) {
                return DragEdge.Right;
            } else {
                return DragEdge.Left;
            }
        } else {
            if (distanceY > 0) {
                return DragEdge.Bottom;
            } else {

                return DragEdge.Top;
            }
        }
    }
    /**
     * get the open status.
     *
     * @return {@link com.isoftstone.swipelayout.SwipeLayout} Open , Close or
     * Middle.
     */
//    public Status getOpenStatus() {
//        Component surfaceView = getSurfaceView();
//        if (surfaceView == null) {
//            return Status.Close;
//        }
//        int surfaceLeft = surfaceView.getLeft();
//        int surfaceTop = surfaceView.getTop();
//        if (surfaceLeft == getPaddingLeft() && surfaceTop == getPaddingTop()) return Status.Close;
//
//        if (surfaceLeft == (getPaddingLeft() - mDragDistance) || surfaceLeft == (getPaddingLeft() + mDragDistance)
//                || surfaceTop == (getPaddingTop() - mDragDistance) || surfaceTop == (getPaddingTop() + mDragDistance))
//            return Status.Open;
//
//        return Status.Middle;
//    }

}
