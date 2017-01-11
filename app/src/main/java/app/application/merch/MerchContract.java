package app.application.merch;

import java.util.ArrayList;

import app.application.base.MvpPresenter;
import app.application.base.MvpView;
import app.application.merch.data.models.MerchItem;
import app.application.merch.data.models.merchlistmodel.MerchListModel;
import app.application.search.SearchPresenter;

/**
 * Specifies the contract between the {@link MerchCategoryFragment} / {@link MerchSearchActivity} and {@link MerchPresenter}.
 */
public interface MerchContract {

    interface View extends MvpView {

        void showMerch(ArrayList<MerchItem> merchItems);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void getMerch(String keywords, String categoryId, String sortOrder);

        ArrayList<MerchItem> mapResults(MerchListModel merch);

        boolean containsMerchItem(ArrayList<MerchItem> merchList, String merchName, String merchImage);

        String getMerchItemImage(String productName);
    }
}
