/* Copyright 2015 Samsung Electronics Co., LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gearvrf.modelviewer2;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import org.gearvrf.GVRActivity;
import org.gearvrf.io.GVRTouchPadGestureListener;
import org.gearvrf.utility.Log;
import org.gearvrf.widgetplugin.GVRWidgetPlugin;

public class ModelViewer2Activity extends GVRActivity
{
    MyMenu mWidget;
    private GVRWidgetPlugin mPlugin;
    private GestureDetector mDetector = null;
    private ModelViewer2Manager mManager = null;

    private GVRTouchPadGestureListener swipeListener = new GVRTouchPadGestureListener()
    {
        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            Log.i(TAG, "onSingleTap");
            mManager.onSingleTap(e);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e)
        {
            Log.i(TAG, "onLongPress");
        }

        @Override
        public boolean onSwipe(MotionEvent e, Action action, float vx, float vy)
        {
            Log.i(TAG, "onSwipe");
            mManager.onSwipe(e, action, vx, vy);
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3)
        {
            mManager.onScroll(arg0, arg1, arg2, arg3);
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        mDetector = new GestureDetector(getBaseContext(), swipeListener);
        mWidget = new MyMenu();
        mPlugin = new GVRWidgetPlugin(this, mWidget);
        mPlugin.setViewSize(2560, 1440);

        //SkyBox List
        mManager = new ModelViewer2Manager(this, mPlugin);
        mPlugin.setMain(mManager);
        mWidget.mManager = mManager;

        setMain(mManager, "gvr.xml");
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        if (mPlugin.getWidgetView() == null)
            return false;
        return mPlugin.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return mDetector.onTouchEvent(event);
    }
}

