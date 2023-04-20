//package com.xd.spring.tencent;
//
//import androidx.annotation.IntDef;
//
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//
//public class CommonConstants {
//        public static final String KEY_EDIT_TYPE = "editType";
//        public static final String EDIT_TYPE_HOME = "home";
//        public static final String EDIT_TYPE_COMPANY = "company";
//        /**
//         * CommonOperateParam 里的 operate 的取值
//         */
//        public static final String ACTION_SELECT_INDEX = "selectIndex";
//        public static final String ACTION_GO_HERE = "goHere";
//        public static final String ACTION_NEXT_PAGE = "nextPage";
//        public static final String ACTION_PRE_PAGE = "prePage";
//        public static final String ACTION_KILL_APP = "killApp";
//        public static final String ACTION_CLEAR_APP_CACHE = "clearAppCache";
//        public static final String ACTION_GET_DISTRICT_INFO = "getDistrictInfo";
//        public static final String ACTION_CONTINUE_NAVI = "continueNavi";
//        @IntDef({
//                RouteCondType.None,
//                RouteCondType.Clear,
//                RouteCondType.Slow,
//                RouteCondType.Jam,
//                RouteCondType.HeavyJam
//        })
//        @Retention(RetentionPolicy.SOURCE)
//        public @interface RouteCondType {
//            /**
//             * 未知
//             */
//            int None = 0;
//            /**
//             * 畅通
//             */
//            int Clear = 1;
//            /**
//             * 缓行
//             */
//            int Slow = 2;
//            /**
//             * 拥堵
//             */
//            int Jam = 3;
//            /**
//             * 严重拥堵
//             */
//            int HeavyJam = 4;
//        }
//    @IntDef({
//            RouteConditionType.ROAD_CONDITION_HIDE,
//            RouteConditionType.ROAD_CONDITION_DISPLAY,
//    })
//    @Retention(RetentionPolicy.SOURCE)
//    public @interface RouteConditionType {
//        /**
//         * 光柱图隐藏
//         */
//        int ROAD_CONDITION_HIDE = 0;
//        /**
//         * 光柱图显示
//         */
//        int ROAD_CONDITION_DISPLAY = 1;
//    }
//    @IntDef({
//            RoadFacilitiesType.ROUTE_CAMERA,
//    })
//    @Retention(RetentionPolicy.SOURCE)
//    public @interface RoadFacilitiesType {
///**
// * 电子眼播报
// */
//int ROUTE_CAMERA = 0;
//    }
//    public interface GoToPageType {
//        public static final String KEY_PAGE_TYPE = "pageType";
//        //0：打开家的设置界⾯
//        public static final int PAGE_TYPE_HOME_SETTING = 1;
//        //1：打开公司的设置界⾯
//        public static final int PAGE_TYPE_COMPANY_SETTING = 2;
//        //打开 poi 详情页
//        public static final int PAGE_TYPE_OPEN_POI_DETAIL = 3;
//        //沿途搜
//        public static final int PAGE_TYPE_ALONG_WAY_SEARCH = 4;
//        //打开更多页面
//        public static final int PAGE_TYPE_SEARCH_MAIN = 5;
//        // 导航去家/去公司
//        public static final int PAGE_TYPE_NAVI_TO_HOME_OR_COMPANY = 6;
//        // 打开搜索结果列表页
//        public static final int PAGE_TYPE_SEARCH_RESULT_LIST = 7;
//        // 导航去某 poi
//        public static final int PAGE_TYPE_GO_POI = 8;
//        // 在 Poi 详情页、路线页或路线详情页，直接开始导航
//        public static final int PAGE_TYPE_DIRECT_NAVI_ON_READY_NAVI_PAGE = 9;
//        // 已经算路成功了, 打开路线页, 然后直接跳到导航页面
//        public static final int PAGE_TYPE_OPEN_ROUTE_DIRECT_NAVI = 10;
//        public static final int PAGE_TYPE_SEARCH_KEYWORD_AND_OPEN_RESULT_LIST = 11;
//        public static final int PAGE_TYPE_SEARCH_NEARBY_AND_OPEN_RESULT_LIST = 12;
//        public static final int PAGE_TYPE_SEARCH_ALONG_WAY_AND_OPEN_RESULT_LIST = 13;
//        // 打开收藏页面
//        public static final int PAGE_TYPE_FAVORITE_LIST = 14;
//        // 周边搜索页面
//        public static final int PAGE_TYPE_AROUND_SEARCH = 15;
//        // 日志选项页面
//        public static final int PAGE_TYPE_LOG_OPTION = 16;
//        // 导航页面
//        public static final int PAGE_TYPE_NAVI = 17;
//    }
//    public interface PagePoiDetail {
//        public static final String KEY_PARAM_TYPE = "paramType";
//        public static final int PARAM_TYPE_POI_ID = 1;
//        public static final int PARAM_TYPE_POI_ADDRESS = 2;
//        public static final int PARAM_TYPE_LATLNG = 3;
//        @Deprecated
//        public static final int PARAM_TYPE_POI_INFO = 4;
//        public static final int PARAM_TYPE_POI_ERROR = 5;
//        public static final int PARAM_TYPE_PANGU_POI_INFO = 6;
//        public static final int PARAM_TYPE_POI_INFO_JSON = 7;
//        public static final String PARAM_NAME_POI_ID = "poiId";
//        public static final String PARAM_NAME_POI_ADDRESS = "address";
//        public static final String PARAM_NAME_LAT = "lat";
//        public static final String PARAM_NAME_LNG = "lng";
//        @Deprecated
//        public static final String PARAM_NAME_POI_INFO = "poiInfo";
//        public static final String PARAM_NAME_POI_ERROR_MSG = "errMsg";
//        public static final String PARAM_NAME_PANGU_POI_INFO = "panguPoiInfo";
//        public static final String PARAM_NAME_POI_INFO_JSON = "poiInfoJson";
//    }
//    public static class LatLng {
//        private double lat;
//        private double lng;
//        public LatLng(double lat, double lng) {
//            this.lat = lat;
//            this.lng = lng;
//        }
//        public double getLat() {
//            return lat;
//        }
//        public void setLat(double lat) {
//            this.lat = lat;
//        }
//        public double getLng() {
//            return lng;
//    }
//        public void setLng(double lng) {
//            this.lng = lng;
//        }
//    }
//    public static class ParamPagePoiInfo {
//        private String poiId;
//        private String poiName;
//        private String address;
//        private LatLng latLng;
//        public String getPoiId() {
//            return poiId;
//        }
//        public void setPoiId(String poiId) {
//            this.poiId = poiId;
//        }
//        public String getPoiName() {
//            return poiName;
//        }
//        public void setPoiName(String poiName) {
//            this.poiName = poiName;
//        }
//        public String getAddress() {
//            return address;
//        }
//        public void setAddress(String address) {
//            this.address = address;
//        }
//        public LatLng getLatLng() {
//            return latLng;
//        }
//        public void setLatLng(LatLng latLng) {
//            this.latLng = latLng;
//        }
//    }
//    public interface GoHomeOrCompany {
//        final String PARAM_NAME_IS_GO_HOME = "isGoHome";
//        final String PARAM_NAME_DIRECT_NAVI_TYPE = "directNaviType";
//        final String PARAM_NAME_IS_AVOID_JAM = "avoidJam";
//        final String PARAM_NAME_IS_NO_HIGHWAY = "noHighway";
//        final String PARAM_NAME_IS_HIGHWAY_FIRST = "highwayFirst";
//        final String PARAM_NAME_IS_NO_TOLL = "noToll";
//        final String PARAM_NAME_VIA_POI_LIST = "viaPoiList";
//        final String PARAM_NAME_START_NODE = "startNode";
//        final String PARAM_NAME_END_NODE = "endNode";
//    }
//    public static class ParamPageGoToPoi {
//        /**
//         * 路线页还是直接到导航页
//         */
//        public int directNaviType = SdkConfig.DirectNaviType.DIRECT_NAVI_TYPE_DEFAULT;
//        public boolean isAvoidJam;
//        public boolean isNoHighway;
//        public boolean isHighwayFirst;
//        public boolean isNoToll;
//        public List<ProtocolPoi> viaPoiList;
//        public ProtocolPoi startNode;
//        public ProtocolPoi endNode;
//    }
//    public static class ParamPageGoToHomeOrCompany extends ParamPageGoToPoi {
//        public boolean isGoHomeOrCompany;
//    }
///**
// * 包括车牌、限行的后台算路和打开导航
// */
//public static class ParamOpenAndNavi extends ParamPageGoToPoi {
//    public String carPlate;
//    public boolean isAvoidRestriction;
//    public int naviBroadcastMode;
//    /**
//     * 2d 北向上/3d 车头向上
//     */
//    public int mapDirectionMode;
//}
//    public enum TtsPlayMode {
//        INTERRUPT(1)/*打断当前播报*/,
//        IGNORABLE(2)/*如果当前有播报, 则忽略*/;
//        public int value;
//        TtsPlayMode(int value) {
//            this.value = value;
//        }
//        public static TtsPlayMode parseValue(int value) {
//            for (TtsPlayMode item : TtsPlayMode.values()) {
//                if (item.value == value) {
//                    return item;
//                }
//            }
//            return null;
//        }
//    }
//    @IntDef({
//            TTSType.TTS_TYPE_LOW,
//            TTSType.TTS_TYPE_NORMAL,
//            TTSType.TTS_TYPE_MEDIUM,
//            TTSType.TTS_TYPE_HIGH,
//    })
//    @Retention(RetentionPolicy.SOURCE)
//    public @interface TTSType {
//        int TTS_TYPE_LOW = 10000;
//        int TTS_TYPE_NORMAL = 10001;
//        int TTS_TYPE_MEDIUM = 10002;
//        int TTS_TYPE_HIGH = 10003;
//        int TTS_TYPE_DEFAULT = TTS_TYPE_LOW;
//    }
//}