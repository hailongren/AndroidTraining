package com.bearapp.androidtraining;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TransitionActivity extends Activity {

    private static final String TAG = TransitionActivity.class.getSimpleName();

    private Scene AScene;
    private Scene AnotherScene;
    private ViewGroup sceneRoot;

    private Button button;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        button = (Button) findViewById(R.id.button);
        sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        AScene = Scene.getSceneForLayout(sceneRoot, R.layout.a_scene, this);
        AnotherScene = Scene.getSceneForLayout(sceneRoot, R.layout.another_scene, this);
        AScene.setEnterAction(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "A scene enter....");
            }
        });
        final Transition fadeTransition = TransitionInflater.from(this)
                .inflateTransition(R.transition.fade_transition);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(AnotherScene, fadeTransition);
            }
        });

    }
}
