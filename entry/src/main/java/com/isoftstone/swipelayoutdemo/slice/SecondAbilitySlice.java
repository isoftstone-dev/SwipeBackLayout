package com.isoftstone.swipelayoutdemo.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.ListContainer;
import ohos.agp.components.StackLayout;
import ohos.agp.components.element.ShapeElement;
import ohos.multimodalinput.event.TouchEvent;

import java.util.Arrays;

/**
 * @author lemon0101
 * @version 1.0
 * @since 2/7
 */
public class SecondAbilitySlice extends AbilitySlice {
    //  private static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x000110, "SecondAbilitySlice");
    /**
     * 容器视图
     */
    private StackLayout containerView;
    /**
     * 我的布局
     */
    private DirectionalLayout myLayout = new DirectionalLayout(this);
    /**
     * 适配器
     */
    private ListDropDownProvider cityAdapter;
    /**
     * 数组
     */
    private String[] citys = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州", "南京", "杭州", "南京", "杭州"};

    @Override
    public void onStart(Intent intent) {

        super.onStart(intent);

        //  super.setUIContent(ResourceTable.Layout_ability_second);
        DirectionalLayout.LayoutConfig config = new DirectionalLayout.LayoutConfig(DirectionalLayout.LayoutConfig.MATCH_PARENT, DirectionalLayout.LayoutConfig.MATCH_PARENT);
        myLayout.setLayoutConfig(config);
        ShapeElement element = new ShapeElement();
        element.setRgbColor(new RgbColor(255, 255, 255));
        myLayout.setBackground(element);

//        Text text = new Text(this);
//        text.setLayoutConfig(config);
//        text.setText("second page");
//        text.setTextColor(new Color(0xFF000000));
//        text.setTextSize(50);
//        text.setTextAlignment(TextAlignment.CENTER);
//        myLayout.addComponent(text);

        containerView = new StackLayout(getContext());
        containerView.setLayoutConfig(new StackLayout.LayoutConfig(StackLayout.LayoutConfig.MATCH_PARENT, StackLayout.LayoutConfig.MATCH_PARENT));
        myLayout.addComponent(containerView);

        final ListContainer cityView = new ListContainer(this);
        cityAdapter = new ListDropDownProvider(Arrays.asList(citys), this);
        //  cityView.setDividerHeight(0);
        cityView.setItemProvider(cityAdapter);
        cityView.setLayoutConfig(new ComponentContainer.LayoutConfig(ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_CONTENT));
//        cityView.setDraggedListener(Component.DRAG_HORIZONTAL_VERTICAL, new Component.DraggedListener() {
//            @Override
//            public void onDragDown(Component component, DragInfo dragInfo) {
//                HiLog.info(label, "secondViewonDragDown");
//            }
//
//            @Override
//            public void onDragStart(Component component, DragInfo dragInfo) {
//
//            }
//
//            @Override
//            public void onDragUpdate(Component component, DragInfo dragInfo) {
//                HiLog.info(label, "secondViewonDragUpdate");
//            }
//
//            @Override
//            public void onDragEnd(Component component, DragInfo dragInfo) {
//
//            }
//
//            @Override
//            public void onDragCancel(Component component, DragInfo dragInfo) {
//
//            }
//        });
        cityView.setTouchEventListener(new Component.TouchEventListener() {
            @Override
            public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
                //   HiLog.info(label, "secondViewonDragTouch");
                return false;
            }
        });
        containerView.addComponent(cityView);
        super.setUIContent(myLayout);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
