package com.enid.rxjavacomm.network;

import com.enid.rxjavacomm.model.AddressInfoModel;
import com.enid.rxjavacomm.model.AddressModel;
import com.enid.rxjavacomm.network.api.SearchApi;
import com.enid.rxjavacomm.util.AddressModel2AddressInfoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Action3;
import rx.functions.ActionN;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Enid on 2016/8/10.
 */
public class ResponseHelper {
    public static void search(Subscription subscription, Observer<AddressModel> observer, String searchKey) {
        subscription = Network.getSearchApi()
                .search("苏州市")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void searchChange(Subscription subscription, Observer<AddressInfoModel> observer) {
        subscription = Network.getSearchApi()
                .search("苏州市")
                .map(AddressModel2AddressInfoModel.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * map函数，参数为一个Func1<I,O>,在call方法中返回O类型，用于将I类型，转换成O类型
     */
    public static void testMap(Subscription subscription) {
        subscription = Observable.from(new String[]{"hello", "this", "is", "a", "tip"})
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s.toUpperCase();
                    }
                })
                .toList()
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> s) {
                        System.out.println("out s : " + s.toString());
                    }
                });
    }


    /**
     * flatMap 参数为一个Func1<I,O>,在call方法中返回O类型，用于将I类型，转换成O类型,不过O类型为Observable类型
     * 可以在flatMap下面的call方法中根I的内容 继续请求数据返回一个Observable<C>处理
     */
    public static void testFlatMap(Subscription subscription, Observer<AddressInfoModel> observer) {
        final SearchApi searchApi = Network.getSearchApi();
        subscription = searchApi.search("苏州市")
                .map(AddressModel2AddressInfoModel.getInstance())
                .flatMap(new Func1<AddressInfoModel, Observable<AddressModel>>() {
                    @Override
                    public Observable<AddressModel> call(AddressInfoModel addressInfoModel) {
                        //这里重复请求了之前的数据，并不是根据 addressInfoModel的内容请求新的数据
                        if (addressInfoModel == null)
                            return Observable.error(new NullPointerException("addressInfoModel is null"));
                        return searchApi.search("苏州市");
                    }
                })
                .map(AddressModel2AddressInfoModel.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void testZip(Subscription subscription) {
        final SearchApi searchApi = Network.getSearchApi();
        subscription = Observable
                .zip(searchApi.search("成都市"), searchApi.search("苏州市"), new Func2<AddressModel, AddressModel, List<AddressModel>>() {
                    @Override
                    public List<AddressModel> call(AddressModel addressModel, AddressModel addressModel2) {
                        List<AddressModel> addressModelList = new ArrayList<AddressModel>();
                        addressModelList.add(addressModel);
                        addressModelList.add(addressModel2);
                        return addressModelList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<AddressModel>>() {
                    @Override
                    public void call(List<AddressModel> addressModels) {
                        for (AddressModel addressModel :
                                addressModels) {
                            System.out.println("---> this is the getCityName " + addressModel.getCityName());
                            System.out.println("---> this is the getAddress " + addressModel.getAddress());
                            System.out.println("---> this is the getLevel " + addressModel.getLevel());
                            System.out.println("---> this is the getLon" + addressModel.getLon());
                            System.out.println("---> this is the getLat" + addressModel.getLat());
                            System.out.println("------------------------------------");
                        }
                    }
                });
    }

    public static void testCache(Subscription subscription) {
        Observable.just("Hello")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "i am enid ho";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });

        String[] arr = new String[]{"A", "B", "C", "D", "E", "F"};
        Observable observableFrom = Observable.from(arr);
        observableFrom.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.toCharArray()[0] > 63 ? true : false;
                    }
                })
                .take(4)
                .doOnNext(new Action1() {
                    @Override
                    public void call(Object o) {
                        //doSomeThing();example save data to sd card
                    }
                });


        //可以简写成上面的Observable.just("Hello")
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onCompleted();
            }
        });


        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext" + s);
            }
        };

        //不完整定义的回调
        Action0 completedAction = new Action0() {
            @Override
            public void call() {
                System.out.println("onComplete");
            }
        };
        Action1<String> successAction1 = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("onSuccess1" + s);
            }
        };
        Action2<String, String> successAction2 = new Action2<String, String>() {
            @Override
            public void call(String s, String s2) {
                System.out.println("onSuccess2" + s + "-" + s2);
            }
        };
        Action3<String, List<String>, Map<String, String>> action3 = new Action3<String, List<String>, Map<String, String>>() {
            @Override
            public void call(String s, List<String> strings, Map<String, String> stringStringMap) {

            }
        };
        ActionN actionN = new ActionN() {
            @Override
            public void call(Object... args) {

            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        observableFrom.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return null;
            }
        });
        Observable observable2Arguments = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("message1");
            }
        });
        observable.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s;
            }
        })
        .flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String s) {
                return null;
            }
        });
//        observable.map(new Func2<String,String,String>(){
//            @Override
//            public String call(String s, String s2) {
//                return null;
//            }
//        });

    }
}
