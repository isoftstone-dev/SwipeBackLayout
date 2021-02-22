package com.isoftstone.swipelayoutdemo.slice;

import com.isoftstone.swipelayout_harmonyos.ResourceTable;
import com.isoftstone.swipelayout.SwipeLayout;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.RecycleItemProvider;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;


import java.util.List;

/**
 * @author lemon0101
 * @version 1.0
 * @since 2/7
 */
public class ListDropDownProvider extends RecycleItemProvider {
    /**
     * 数据list
     */
    private List<String> list;
    /**
     * 展示ListContainer的slice
     */
    private AbilitySlice slice;
    /**
     * 选中的itemPosition
     */
    private int checkItemPosition = 0;

    /**
     * 构造函数
     *
     * @param list  list
     * @param slice slice
     */
    public ListDropDownProvider(List<String> list, AbilitySlice slice) {
        this.list = list;
        this.slice = slice;
    }

    /**
     * 设置选中的位置
     *
     * @param position position
     */
    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component convertComponent, ComponentContainer componentContainer) {
        ViewHolder viewHolder;

        if (convertComponent == null) {
//            convertComponent = LayoutScatter.getInstance(slice)
//                    .parse(ResourceTable.Id_sample1, null, false);
            DirectionalLayout layout = (DirectionalLayout) LayoutScatter.getInstance(slice)
                    .parse(ResourceTable.Layout_sample, null, false);

            DirectionalLayout layout1 = (DirectionalLayout) layout.findComponentById(ResourceTable.Id_total1);
            SwipeLayout swipeLayout = (SwipeLayout) layout1.findComponentById(ResourceTable.Id_sample2);
            // swipeLayout.setLayoutConfig(new DirectionalLayout.LayoutConfig(ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_CONTENT));
            DirectionalLayout right = (DirectionalLayout) swipeLayout.findComponentById(ResourceTable.Id_bottom_wrapper1);
            swipeLayout.initializeSwipe();
            DirectionalLayout left = (DirectionalLayout) swipeLayout.findComponentById(ResourceTable.Id_bottom_fronts);
            Image image3 = (Image) swipeLayout.findComponentById(ResourceTable.Id_images3);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, right);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right, left);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Bottom, image3);
            viewHolder = new ViewHolder();
            // viewHolder.myText = (Text) convertComponent.findComponentById(ResourceTable.Id_text);
            swipeLayout.setTag(viewHolder);
            convertComponent = swipeLayout;
//            convertComponent = LayoutScatter.getInstance(slice)
//                    .parse(ResourceTable.Layout_item_default_drop_down, null, false);
//            viewHolder = new ViewHolder();
//            viewHolder.myText = (Text) convertComponent.findComponentById(ResourceTable.Id_text);
//            convertComponent.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertComponent.getTag();
        }
        //   fillValue(position,viewHolder);
        return convertComponent;
    }

    /**
     * 展示数据
     *
     * @param position   position
     * @param viewHolder viewHolder
     */
    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.myText.setText(list.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.myText.setTextColor(new Color(Color.getIntColor("#7B1FA2")));
//                // 创建背景元素
//                ShapeElement textShapeElement = new ShapeElement();
//                // 设置绿色
//
//                textShapeElement.setRgbColor(RgbColor.fromRgbaInt(Color.getIntColor("#f2f2f2")));
//                viewHolder.myText.setBackground(textShapeElement);
            } else {
                viewHolder.myText.setTextColor(new Color(Color.getIntColor("#111111")));
                // 创建背景元素
                ShapeElement textShapeElement = new ShapeElement();
                // 设置白色
                textShapeElement.setRgbColor(new RgbColor(Color.WHITE.getValue()));
                viewHolder.myText.setBackground(textShapeElement);
            }
        }
    }

    /**
     * ViewHolder
     */
    class ViewHolder {
        /**
         * text
         */
       private Text myText;

        public Text getMyText() {
            return myText;
        }

        public void setMyText(Text myText) {
            this.myText = myText;
        }
    }
}
