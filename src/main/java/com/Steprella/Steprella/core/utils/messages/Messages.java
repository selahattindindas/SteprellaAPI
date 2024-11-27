package com.Steprella.Steprella.core.utils.messages;

public class Messages {

    public static class Error {
        public static final String CUSTOM_BAD_REQUEST = "Sunucu hatası. Lütfen daha sonra tekrar deneyiniz!";
        public static final String CUSTOM_NOT_FOUND = "Verilen bilgiye ait kaynak bulunamadı.";
        public static final String CUSTOM_CATEGORY_NOT_FOUND = "Böyle bir kategori bulunamadı.";
        public static final String CUSTOM_CITY_NOT_FOUND = "Böyle bir şehir bulunamadı.";
        public static final String CUSTOM_DISTRICT_NOT_FOUND = "Böyle bir ilçe bulunamadı";
        public static final String INVALID_DISTRICT_CITY_RELATION = "Bu şehire bağlı böyle bir ilçe yok";
        public static final String CUSTOM_BRAND_NOT_FOUND = "Böyle bir ayakkabı markası bulunamadı.";
        public static final String CUSTOM_COMMENT_NOT_FOUND = "Böyle bir yorum bulunamadı.";
        public static final String CUSTOM_FILE_NOT_FOUND = "Böyle bir fotoğraf bulunamadı.";
        public static final String CUSTOM_SHOE_MODEL_NOT_FOUND = "Böyle bir ayakkabı modeli bulunamadı.";
        public static final String CUSTOM_COLOR_NOT_FOUND = "Böyle bir renk bulunamadı";
        public static final String CUSTOM_PRODUCT_SIZE_NOT_FOUND = "Böyle bir ürün ayakkabısı bulunamadı";
        public static final String CUSTOM_ADDRESS_NOT_FOUND = "Böyle bir adres bulunamadı";
        public static final String CUSTOM_CART_NOT_FOUND = "Böyle bir sepet bulunamadı.";
        public static final String CUSTOM_CART_ITEM_NOT_FOUND = "Böyle bir sepet öğesi bulunamadı.";
        public static final String CUSTOM_USER_NOT_FOUND = "Böyle bir kullanıcı bulunamadı.";
        public static final String CUSTOM_PRODUCT_NOT_FOUND = "Böyle bir ürün bulunamadı.";
        public static final String CUSTOM_FILTER_PRODUCT_NOT_FOUND = "Aranan kriterlere ait ürün bulunamadı.";
        public static final String FILE_SIZE_EXCEEDED = "Dosya boyutu, izin verilen maksimum boyutu (%s) aşıyor.";
        public static final String FILE_UPLOAD_FAILED = "Dosya yüklenirken bir hata oluştu: %s";
        public static final String FILE_DELETION_FAILED = "Dosya silinirken bir hata oluştu: %";
        public static final String USER_WITH_SAME_EMAIL_EXISTS = "Böyle bir e-posta adresi mevcuttur.";
        public static final String INVALID_SIZE_FOR_VARIANT = "Geçersiz boyut numarası! Bu varyant için geçerli boyut değil.";
        public static final String INSUFFICIENT_STOCK = "Yeterli stok bulunmamaktadır. Talep edilen miktar: %s";
        public static final String CART_ALREADY_EXISTS = "Bu kullanıcıya ait bir sepet bulunmaktadır.";
        public static final String PRODUCT_VARIANT_ALREADY_EXISTS = "Bu renge sahip ürün bulunmaktadır.";
    }

    public static class Success {
        public static final String CUSTOM_SUCCESSFULLY = "İşlem Başarıyla Gerçekleşti";
    }
}
