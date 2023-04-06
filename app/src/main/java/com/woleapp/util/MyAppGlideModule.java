package com.woleapp.util;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by mangesh on 10/1/19.
 */

@GlideModule

/*It's like BindingAdapter in DataBinding. This class will automatically be found
with the help of @GlideModule, no matter where is it placed*/

public final class MyAppGlideModule extends AppGlideModule {
    // leave empty for now
}
