package com.isoftstone.swipelayoutdemo.slice;


import com.isoftstone.swipelayout_harmonyos.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.isoftstone.swipelayout.SwipeLayout;
import ohos.aafwk.content.Operation;

import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.Text;
import ohos.agp.window.dialog.ToastDialog;


/**
 * @author lemon0101
 * @version 1.0
 * @since 2/7
 */
public class MainAbilitySlice extends AbilitySlice {
    //  private static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x000110, "MainAbilitySlice");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
//        DirectionalLayout layout = (DirectionalLayout) LayoutScatter.getInstance(this)
//                .parse(ResourceTable.Layout_ability_main, null, false);
        DirectionalLayout layout = (DirectionalLayout) findComponentById(ResourceTable.Id_total);
        SwipeLayout swipeLayout = (SwipeLayout) findComponentById(ResourceTable.Id_sample1);
        DirectionalLayout right = (DirectionalLayout) findComponentById(ResourceTable.Id_bottom_wrapper);
        swipeLayout.initializeSwipe();
        DirectionalLayout left = (DirectionalLayout) findComponentById(ResourceTable.Id_bottom_front);
        Image image3 = (Image) findComponentById(ResourceTable.Id_image3);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, right);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, left);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Bottom, image3);
//        HiLog.info(label, "Log_width" + swipeLayout.getWidth());
//        bottom.setContentPosition(DeviceUtils.getScreenSize(this.getContext()).getPointXToInt(), 0);
        Image image1 = (Image) left.findComponentById(ResourceTable.Id_image1);
        image1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                new ToastDialog(MainAbilitySlice.this).setText("收藏").show();
            }
        });
        Image image2 = (Image) left.findComponentById(ResourceTable.Id_image2);
        image2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                new ToastDialog(MainAbilitySlice.this).setText("删除").show();
            }
        });
        Text text1 = (Text) right.findComponentById(ResourceTable.Id_Text1);
        text1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                new ToastDialog(MainAbilitySlice.this).setText("收藏").show();
            }
        });
        Text text2 = (Text) right.findComponentById(ResourceTable.Id_text2);
        text2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                new ToastDialog(MainAbilitySlice.this).setText("删除").show();
            }
        });
        DirectionalLayout total = (DirectionalLayout) findComponentById(ResourceTable.Id_total);
        Image image = (Image) total.findComponentById(ResourceTable.Id_image);
        image.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                new ToastDialog(MainAbilitySlice.this).setText("跳转").show();
                Intent secondIntent = new Intent();
                // 指定待启动FA的bundleName和abilityName
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.isoftstone.swipelayout_harmonyos")
                        .withAbilityName("com.isoftstone.swipelayout_harmonyos.SecondAbility")
                        .build();
                secondIntent.setOperation(operation);
                startAbility(secondIntent); // 通过AbilitySlice的startAbility接口实现启动另一个页面
            }
        });

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
