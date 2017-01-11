package app.application.merch;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.application.base.BasePresenter;
import app.application.merch.data.MerchRepository;
import app.application.merch.data.models.MerchItem;
import app.application.merch.data.models.merchlistmodel.Item;
import app.application.merch.data.models.merchlistmodel.MerchListModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.lang.Float.parseFloat;

/**
 * Listens to user actions from the UI ({@link MerchCategoryFragment} and {@link MerchSearchActivity}), retrieves the data and updates
 * the UI as required.
 */
public class MerchPresenter extends BasePresenter<MerchContract.View> implements MerchContract.Presenter {

    public MerchRepository merchRepository;
    private ArrayList<MerchItem> merchItems;
    private int pageNum;

    @Inject
    public MerchPresenter(MerchRepository merchRepository) {
        this.merchRepository = merchRepository;
        merchItems = new ArrayList<>();
        pageNum = 1;
    }

    @Override
    public void getMerch(String keywords, String categoryId, String sortOrder) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(merchRepository.getMerch(keywords, categoryId, sortOrder, pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MerchListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("There was a problem loading this page");
                    }

                    @Override
                    public void onNext(MerchListModel merchListModel) {
                        getView().hideLoading();
                        ArrayList<MerchItem> results = mapResults(merchListModel);
                        if(results.size() == 0) {
                            getView().showError("No results for " + keywords + ".");
                        } else {
                            pageNum++;
                            getView().showMerch(results);
                        }
                    }
                }));
    }

    /**
     * Returns a list of {@link MerchItem}s that contains data mapped from the
     * {@link MerchListModel} that is returned from the api response
     *
     * @param merch merch returned from the api response
     * @return list of {@link MerchItem}s to display in the UI
     */
    @Override
    public ArrayList<MerchItem> mapResults(MerchListModel merch) {
        if (merch.findItemsAdvancedResponse.get(0).searchResult.size() > 0) {
            List<Item> item = merch.findItemsAdvancedResponse.get(0).searchResult.get(0).item;
            for (int i = 0; i < item.size(); i++) {
                String itemName = item.get(i).title.get(0);
                //String itemUrl = item.get(i).viewItemURL.get(0);
                String itemPic = "http://thumbs1.ebaystatic.com/pict/04040_0.jpg";
                float totalPrice;
                //if the item has any pictures
                if (item.get(i).galleryURL != null) {
                    itemPic = item.get(i).galleryURL.get(0);
                }
                String itemPrice = item.get(i).sellingStatus.get(0).convertedCurrentPrice.get(0).__value__;
                //if the item has any shipping costs
                if (item.get(i).shippingInfo.get(0).shippingServiceCost != null) {
                    String itemShipPrice = item.get(i).shippingInfo.get(0).shippingServiceCost.get(0).__value__;
                    totalPrice = parseFloat(itemPrice) + parseFloat(itemShipPrice);
                } else {
                    totalPrice = parseFloat(itemPrice);
                }
                MerchItem merchItem = new MerchItem();
                merchItem.setName(itemName);
                merchItem.setImage(itemPic);
                DecimalFormat decimal = new DecimalFormat("##.00");
                merchItem.setPrice("$" + String.valueOf(decimal.format(totalPrice)));
                //if the merch item doesnt have the 404 image and it isnt already in the list of merch
                if (!itemPic.equals("http://thumbs1.ebaystatic.com/pict/04040_0.jpg") && !containsMerchItem(merchItems, itemName, itemPic)) {
                    merchItems.add(merchItem);
                }
            }
        }
        return merchItems;
    }

    /**
     * Returns a boolean indicating if the current item of merch is already present in the list
     * of displayed merch
     *
     * @param merchList list of currently displayed merch
     * @param merchName name of the current merch item
     * @param merchImage image of the current merch item
     * @return boolean indicating if the cuttent item is already in the {@link MerchItem}s list
     */
    @Override
    public boolean containsMerchItem(ArrayList<MerchItem> merchList, String merchName, String merchImage){
        boolean doesContain = false;
        for (MerchItem merchItem: merchList) {
            if(merchItem.getName().equals(merchName) || merchItem.getImage().equals(merchImage)){
                doesContain = true;
            }
        }
        return doesContain;
    }

    /**
     * Returns the url of the merch item with the given productName
     *
     * @param productName name of the product
     * @return url containing an image of the merch item
     */
    @Override
    public String getMerchItemImage(String productName){
        String image = "http://thumbs1.ebaystatic.com/pict/04040_0.jpg";
        if(merchItems.size() > 0) {
            for (MerchItem merchItem : merchItems) {
                String itemName = merchItem.getName().toString();
                if (itemName.trim().equals(productName.trim())) {
                    image = merchItem.getImage();
                    break;
                }
            }
        }
        return image;
    }
}
