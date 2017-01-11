package app.application.merch.data;

import app.application.merch.data.models.merchlistmodel.MerchListModel;
import rx.Observable;

/**
 * Repository to search for merch
 */
public interface MerchRepository {
    Observable<MerchListModel> getMerch(String keywords, String categoryId, String sortOrder, int page);
}

