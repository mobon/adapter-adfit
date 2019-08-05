package com.mobon.adfit_sdk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kakao.adfit.ads.AdListener;
import com.kakao.adfit.ads.ba.BannerAdView;

import org.json.JSONException;
import org.json.JSONObject;

public class AdfitAdapter {

    private static final String TAG = "Mobon_AdfitAdapter";

    private Context mContext;
    private String PLACEMENT_PARAMETER;
    private BannerAdView mBannerAdView;
    private int mAdType;
    private Dialog mInterstitialAd;
    private AlertDialog mEndingAd;
    private boolean isInterstitialLoad;
    private boolean isTestMode;
    private View.OnClickListener mEndingListener;
    private View.OnClickListener mInterstitialListener;


    public AdfitAdapter(Context context) {
        mContext = context;
    }

    public AdfitAdapter(Context context, String _key) {
        mContext = context;
    }

    public void setLog(boolean is) {
    }

    public void setTestMode(boolean is) {
        isTestMode = is;
    }

    public void init(String key, int adType) {
        destroy();
        this.PLACEMENT_PARAMETER = key;
        this.mAdType = adType;

        switch (adType) {
            case MediationAdSize.BANNER_320_50:
                mBannerAdView = new BannerAdView(mContext);
                mBannerAdView.setClientId(PLACEMENT_PARAMETER);
                mBannerAdView.setTestMode(isTestMode);
                // setBannerLayoutParams(320,50);
                break;
            case MediationAdSize.BANNER_320_100:
                mBannerAdView = new BannerAdView(mContext);
                mBannerAdView.setClientId(PLACEMENT_PARAMETER);
                mBannerAdView.setTestMode(isTestMode);
                //  setBannerLayoutParams(320,100);
                break;
            case MediationAdSize.BANNER_300_250:
                mBannerAdView = new BannerAdView(mContext);
                mBannerAdView.setClientId(PLACEMENT_PARAMETER);
                mBannerAdView.setTestMode(isTestMode);
                //  setBannerLayoutParams(300,250);
                break;
            case MediationAdSize.INTERSTITIAL:
                mBannerAdView = new BannerAdView(mContext);
                mBannerAdView.setClientId(PLACEMENT_PARAMETER);
                mBannerAdView.setTestMode(isTestMode);

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.adfit_interstitial_layout, null);
                if (view != null) {
                    LinearLayout ad_container = view.findViewById(R.id.ad_container);
                    ad_container.removeAllViews();
                    ad_container.addView(mBannerAdView);

                    ImageButton cancel = view.findViewById(R.id.cancel_btn);
                    cancel.setBackgroundColor(Color.TRANSPARENT);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mInterstitialAd != null && mInterstitialAd.isShowing())
                                mInterstitialAd.dismiss();

                            try {
                                JSONObject obj = new JSONObject();
                                obj.put("code", MediationAdCode.AD_LISTENER_CODE_AD_CLOSE);
                                obj.put("msg", "");
                                v.setTag(obj);
                                mInterstitialListener.onClick(v);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.AdfitDialogTheme);
//                builder.setView(view);
//                builder.setCustomTitle(null);
//                mInterstitialAd = builder.create();

                mInterstitialAd = new Dialog(mContext,R.style.AdfitDialogTheme);
                mInterstitialAd.setContentView(view);

                mInterstitialAd.setOnKeyListener(new Dialog.OnKeyListener() {

                    @Override
                    public boolean onKey(DialogInterface arg0, int keyCode,
                                         KeyEvent event) {
                        // TODO Auto-generated method stub
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            mInterstitialAd.dismiss();

                            try {
                                JSONObject obj = new JSONObject();
                                obj.put("code", MediationAdCode.AD_LISTENER_CODE_AD_CLOSE);
                                obj.put("msg", "");
                                View v = new View(mContext);
                                v.setTag(obj);
                                mInterstitialListener.onClick(v);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return true;
                    }
                });



                break;
            case MediationAdSize.NATIVE:
                //  mNativeAd = new InMobiNative(mContext,PLACEMENT_PARAMETER);
                break;
            case MediationAdSize.ENDING:
                mBannerAdView = new BannerAdView(mContext);
                mBannerAdView.setClientId(PLACEMENT_PARAMETER);
                mBannerAdView.setTestMode(isTestMode);
                LayoutInflater inflater2 = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view2 = inflater2.inflate(R.layout.adfit_ending_layout, null);
                if (view2 != null) {
                    LinearLayout ad_container = view2.findViewById(R.id.ad_container);
                    ad_container.removeAllViews();
                    ad_container.addView(mBannerAdView);

                   Button cancel = view2.findViewById(R.id.cancel_btn);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mEndingAd != null && mEndingAd.isShowing())
                                mEndingAd.dismiss();

                            try {
                                JSONObject obj = new JSONObject();
                                obj.put("code", MediationAdCode.AD_LISTENER_CODE_AD_CLOSE);
                                obj.put("msg", "");
                                v.setTag(obj);
                                mEndingListener.onClick(v);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    Button ok = view2.findViewById(R.id.ok_btn);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mEndingAd != null && mEndingAd.isShowing())
                                mEndingAd.dismiss();
                            try {
                                JSONObject obj = new JSONObject();
                                obj.put("code", MediationAdCode.AD_LISTENER_CODE_FINISH_CLICK);
                                obj.put("msg", "");
                                v.setTag(obj);
                                mEndingListener.onClick(v);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
                builder2.setView(view2);
                builder2.setCustomTitle(null);
                mEndingAd = builder2.create();


                mEndingAd.setOnKeyListener(new Dialog.OnKeyListener() {

                    @Override
                    public boolean onKey(DialogInterface arg0, int keyCode,
                                         KeyEvent event) {
                        // TODO Auto-generated method stub
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            mEndingAd.dismiss();

                            try {
                                JSONObject obj = new JSONObject();
                                obj.put("code", MediationAdCode.AD_LISTENER_CODE_AD_CLOSE);
                                obj.put("msg", "");
                                View v = new View(mContext);
                                v.setTag(obj);
                                mEndingListener.onClick(v);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return true;
                    }
                });

                break;
            case MediationAdSize.VIDEO:

                break;
            default:
                mBannerAdView = new BannerAdView(mContext);
                mBannerAdView.setClientId(PLACEMENT_PARAMETER);
                mBannerAdView.setTestMode(isTestMode);
                //   setBannerLayoutParams(320,50);
                break;
        }
    }

    public Object getBannerView() {
        return mBannerAdView;
    }

    public Object getInterstitialView() {
        return mInterstitialAd;
    }


    public Object geEndingView() {
        if (mEndingAd != null) {
            return mEndingAd;
        }
        return null;
    }

    public void setAdListener(final View.OnClickListener _listner) {

        final View v = new View(mContext);
        if (mAdType == MediationAdSize.INTERSTITIAL && mInterstitialAd != null) {
            mInterstitialListener = _listner;
            mBannerAdView.setAdListener(new AdListener() {
                @Override
                public void onAdFailed(int errorCode) {
                    isInterstitialLoad = false;
                    Log.d(TAG, "Banner ad failed to load with error: " +
                            errorCode);

                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("code", MediationAdCode.AD_LISTENER_CODE_ERROR);
                        obj.put("msg", errorCode);
                        v.setTag(obj);
                        _listner.onClick(v);
                        mInterstitialAd = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdLoaded() {
                    isInterstitialLoad = true;
                    Log.d(TAG, "onAdLoadSucceeded");
                    if(!mInterstitialAd.isShowing())
                        mInterstitialAd.show();
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("code", MediationAdCode.AD_LISTENER_CODE_AD_LOAD);
                        v.setTag(obj);
                        _listner.onClick(v);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdClicked() {
                    Log.d(TAG, "onAdLoadSucceeded");
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("code", MediationAdCode.AD_LISTENER_CODE_AD_CLICK);
                        v.setTag(obj);
                        _listner.onClick(v);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });

        } else if (mAdType == MediationAdSize.ENDING && mEndingAd != null) {
            mEndingListener = _listner;
            mBannerAdView.setAdListener(new AdListener() {
                @Override
                public void onAdFailed(int errorCode) {
                    isInterstitialLoad = false;
                    mEndingAd = null;
                    Log.d(TAG, "Banner ad failed to load with error: " +
                            errorCode);

                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("code", MediationAdCode.AD_LISTENER_CODE_ERROR);
                        obj.put("msg", errorCode);
                        v.setTag(obj);
                        _listner.onClick(v);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdLoaded() {
                    isInterstitialLoad = true;
                    Log.d(TAG, "onAdLoadSucceeded");
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("code", MediationAdCode.AD_LISTENER_CODE_AD_LOAD);
                        v.setTag(obj);
                        _listner.onClick(v);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdClicked() {
                    Log.d(TAG, "onAdLoadSucceeded");
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("code", MediationAdCode.AD_LISTENER_CODE_AD_CLICK);
                        v.setTag(obj);
                        _listner.onClick(v);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });

        } else if (mBannerAdView != null) {
            mBannerAdView.setTestMode(false);
            mBannerAdView.setAdListener(new AdListener() {
                @Override
                public void onAdFailed(int errorCode) {
                    Log.d(TAG, "Banner ad failed to load with error: " +
                            errorCode);

                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("code", MediationAdCode.AD_LISTENER_CODE_ERROR);
                        obj.put("msg", errorCode);
                        v.setTag(obj);
                        _listner.onClick(v);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdLoaded() {
                    Log.d(TAG, "onAdLoadSucceeded");
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("code", MediationAdCode.AD_LISTENER_CODE_AD_LOAD);
                        v.setTag(obj);
                        _listner.onClick(v);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdClicked() {
                    Log.d(TAG, "onAdLoadSucceeded");
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("code", MediationAdCode.AD_LISTENER_CODE_AD_CLICK);
                        v.setTag(obj);
                        _listner.onClick(v);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });


        }


    }

    public void loadAd() {

        try {
            if (mBannerAdView != null)
                mBannerAdView.loadAd();
        } catch (Exception e) {
            System.out.println("adfit loadAd : " + e.getMessage());
        }

    }

    public boolean isLoaded() {
        if (mAdType == MediationAdSize.INTERSTITIAL && mInterstitialAd != null) {
            return true;
        } else if (mAdType == MediationAdSize.ENDING && mEndingAd != null) {
            return true;
        }

        return false;
    }

    public boolean show() {
        if (mAdType == MediationAdSize.INTERSTITIAL && mInterstitialAd != null) {
            mInterstitialAd.show();
            return true;
        } else if (mAdType == MediationAdSize.ENDING && mEndingAd != null) {
            mEndingAd.show();
            return true;
        }


        return false;
    }

    public void destroy() {
        if (mBannerAdView != null)
            mBannerAdView.destroy();

        if (mInterstitialAd != null)
            mInterstitialAd = null;

        if (mEndingAd != null)
            mEndingAd = null;
    }


    private void setBannerLayoutParams(int _width, int _height) {
        int width = toPixelUnits(_width);
        int height = toPixelUnits(_height);
        RelativeLayout.LayoutParams bannerLayoutParams = new RelativeLayout.LayoutParams(width, height);
        bannerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bannerLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mBannerAdView.setLayoutParams(bannerLayoutParams);
    }

    private int toPixelUnits(int dipUnit) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return Math.round(dipUnit * density);
    }

}