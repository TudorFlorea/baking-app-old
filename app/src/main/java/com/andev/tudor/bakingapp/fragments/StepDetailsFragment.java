package com.andev.tudor.bakingapp.fragments;

import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andev.tudor.bakingapp.data.Step;
import com.andev.tudor.bakingapp.utils.Constants;
import com.andev.tudor.bakingapp.utils.InterfaceUtils.RecipeStepListener;

import com.andev.tudor.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment implements SimpleExoPlayer.EventListener{

    @BindView(R.id.player_view) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.step_description_tv) TextView mStepDescriptionTV;
    @BindView(R.id.previous_step_btn) Button mPreviousStepBtn;
    @BindView(R.id.next_step_btn) Button mNextStepBtn;

    private SimpleExoPlayer mPlayer;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private RecipeStepListener mListener;
    private ArrayList<Step> mSteps;
    private int mCurrentStepIndex;

    private final String STEP_EXTRA = "step_extra";
    private final String PLAYER_POSITION_KEY = "position";

    public StepDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        ButterKnife.bind(this, view);

        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {

            mSteps = getArguments().getParcelableArrayList(Constants.STEPS_ARRAY_LIST_TAG);
            mCurrentStepIndex = getArguments().getInt(Constants.CURRENT_STEP_INDEX_TAG);

            if (mSteps == null) {
                Toast.makeText(getActivity(), "Null " + mCurrentStepIndex, Toast.LENGTH_LONG).show();
            }

            Step step = mSteps.get(mCurrentStepIndex);

            Log.v("FRAGM", "HEREEE");

            if (!step.getVideoUrl().equals("")) {
                mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.question_mark));
                initMediaSession();
                Log.v("Player", step.getVideoUrl());

                initPlayer(Uri.parse(step.getVideoUrl()));

                if (savedInstanceState != null) {

                    long position = savedInstanceState.getLong(PLAYER_POSITION_KEY);

                    mPlayer.seekTo(position);
                }

            } else {
                mPlayerView.setVisibility(View.GONE);
            }

            mStepDescriptionTV.setText(step.getDescription());

        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }



    }

    private void initMediaSession() {

        mMediaSession = new MediaSessionCompat(getActivity(), "a");
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        );

        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE
                );

        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback(new BakingSessionCallback());

        mMediaSession.setActive(true);


    }

    private void initPlayer(Uri mediaUri) {
        if (mPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mPlayer);

            mPlayer.addListener(this);

            String userAgent = "Test Agent";

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null
            );

            mPlayer.prepare(mediaSource);

            mPlayer.setPlayWhenReady(true);
        }
    }

    private void releaseResources() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }

        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }

    private class BakingSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mPlayer.seekTo(0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseResources();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Toast.makeText(getActivity(), "Changed orietation", Toast.LENGTH_SHORT).show();
        // Checking the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
//            params.width = params.MATCH_PARENT;
//            params.height = params.MATCH_PARENT;
//            mPlayerView.setLayoutParams(params);
//            if(getActivity().getActionBar()!=null) {
//                getActivity().getActionBar().hide();
//            }
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
//            params.width = params.MATCH_PARENT;
//            params.height = 600;
//            mPlayerView.setLayoutParams(params);
//            if(getActivity().getActionBar()!=null) {
//                getActivity().getActionBar().show();
//            }
//        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        long playerPosition = mPlayer.getCurrentPosition();

        outState.putLong(PLAYER_POSITION_KEY, playerPosition);

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady ) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mPlayer.getCurrentPosition(), 1f);
        }

        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
