package com.isoftstone.swipelayoutdemo;

import com.isoftstone.swipelayoutdemo.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * @author lemon0101
 * @version 1.0
 * @since 2/7
 */
public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
    }
}
