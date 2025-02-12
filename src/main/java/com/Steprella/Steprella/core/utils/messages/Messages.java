package com.Steprella.Steprella.core.utils.messages;

public class Messages {

    public static class Error {
        public static final String CUSTOM_BAD_REQUEST = "Geçersiz istek. Lütfen gönderdiğiniz verileri kontrol edin.";
        public static final String CUSTOM_INTERNAL_SERVER_ERROR = "Beklenmedik bir sunucu hatası oluştu. Lütfen sonra tekrar deneyiniz.";
        public static final String TOKEN_EXPIRED = "Oturum süreniz dolmuş. Lütfen tekrar giriş yapın.";
        public static final String CUSTOM_CATEGORY_NOT_FOUND = "Böyle bir kategori bulunamadı.";
        public static final String CUSTOM_CITY_NOT_FOUND = "Böyle bir şehir bulunamadı.";
        public static final String FAVORITE_ALREADY_EXISTS = "Bu ürün zaten favorilerinizde bulunuyor";
        public static final String CUSTOM_DISTRICT_NOT_FOUND = "Böyle bir ilçe bulunamadı";
        public static final String INVALID_DISTRICT_CITY_RELATION = "Seçilen ilçe bu ile ait değil";
        public static final String ADDRESS_NOT_BELONG_TO_USER = "Bu kullanıcının böyle bir adresi bulunmamaktadır.";
        public static final String CUSTOM_BRAND_NOT_FOUND = "Böyle bir ayakkabı markası bulunamadı.";
        public static final String CUSTOM_FEATURE_NOT_FOUND = "Böyle bir ek özellik bulunamadı.";
        public static final String CUSTOM_MATERIAL_NOT_FOUND = "Böyle bir materyal bulunamadı.";
        public static final String CUSTOM_USAGE_AREA_NOT_FOUND = "Böyle bir kullanım alanı bulunamadı.";
        public static final String CUSTOM_CART_ITEMS_NOT_FOUND = "Belirtilen sepet öğeleri bulunamadı veya kullanıcıya ait değil.";
        public static final String CUSTOM_PRODUCT_ALREADY_IN_CART = "Bu ürün zaten sepetinizde mevcut.";
        public static final String CUSTOM_COMMENT_NOT_FOUND = "Yorum bulunamadı";
        public static final String CUSTOM_FILE_NOT_FOUND = "Böyle bir fotoğraf bulunamadı.";
        public static final String CUSTOM_SHOE_MODEL_NOT_FOUND = "Böyle bir ayakkabı modeli bulunamadı.";
        public static final String CUSTOM_COLOR_NOT_FOUND = "Böyle bir renk bulunamadı";
        public static final String CUSTOM_PRODUCT_SIZE_NOT_FOUND = "Böyle bir ürün ayakkabısı bulunamadı";
        public static final String CUSTOM_ORDER_NOT_FOUND = "Böyle bir sipariş bulunamadı";
        public static final String CUSTOM_ORDER_ITEM_NOT_FOUND = "Böyle bir sipariş öğesi bulunamadı.";
        public static final String CUSTOM_ADDRESS_NOT_FOUND = "Böyle bir adres bulunamadı";
        public static final String CUSTOM_CART_NOT_FOUND = "Böyle bir sepet bulunamadı.";
        public static final String CUSTOM_CART_ITEM_NOT_FOUND = "Böyle bir sepet öğesi bulunamadı.";
        public static final String CUSTOM_USER_NOT_FOUND = "Böyle bir kullanıcı bulunamadı.";
        public static final String CUSTOM_PRODUCT_NOT_FOUND = "Böyle bir ürün bulunamadı.";
        public static final String CUSTOM_EMAIL_NOT_FOUND = "Böyle bir e-posta adresi bulunamadı.";
        public static final String CUSTOM_FILTER_PRODUCT_NOT_FOUND = "Aranan kriterlere ait ürün bulunamadı.";
        public static final String CUSTOM_ADDRESS_ACCESS_DENIED = "Bu adrese erişim yetkiniz bulunmamaktadır";
        public static final String FILE_SIZE_EXCEEDED = "Dosya boyutu, izin verilen maksimum boyutu (%s) aşıyor.";
        public static final String FILE_UPLOAD_FAILED = "Dosya yüklenirken bir hata oluştu: %s";
        public static final String FILE_DELETION_FAILED = "Dosya silinirken bir hata oluştu: %";
        public static final String USER_WITH_SAME_EMAIL_EXISTS = "Böyle bir e-posta adresi mevcuttur.";
        public static final String INVALID_SIZE_FOR_VARIANT = "Geçersiz boyut numarası! Bu varyant için geçerli boyut değil.";
        public static final String INSUFFICIENT_STOCK = "Yeterli stok bulunmamaktadır. Talep edilen miktar: %s";
        public static final String CART_ALREADY_EXISTS = "Bu kullanıcıya ait bir sepet bulunmaktadır.";
        public static final String PRODUCT_VARIANT_ALREADY_EXISTS = "Bu renge sahip ürün bulunmaktadır.";
        public static final String CUSTOM_EMAIL_ALREADY_EXISTS = "Bu e-posta adresi zaten kullanılıyor!";
        public static final String ACCESS_DENIED = "Bu kaynağa erişim izniniz yok.";
        public static final String VERIFICATION_CODE_INVALID ="Geçersiz veya süresi dolmuş doğrulama kodu.";
        public static final String EMAIL_VERIFICATION_REQUIRED = "Erişim için e-posta doğrulaması gerekiyor.";
        public static final String EMAIL_FILE_UPLOAD_FAILED = "E-posta gönderilirken bir hata oluştu.";
        public static final String ACCOUNT_NOT_ACTIVATED = "Lütfen önce hesabınızı onaylayın.";
        public static final String INVALID_VERIFICATION_CODE = "Geçersiz veya süresi dolmuş doğrulama kodu. Lütfen tekrar deneyin.";
        public static final String CUSTOM_CUSTOMER_NOT_FOUND = "Müşteri bulunamadı";
        public static final String CUSTOM_CUSTOMER_ALREADY_EXISTS = "Bu kullanıcı için müşteri kaydı zaten mevcut";
        public static final String CUSTOM_CUSTOMER_REQUIRED = "Müşteri bilgileri zorunludur";
        public static final String CUSTOM_CUSTOMER_INVALID = "Geçersiz müşteri bilgileri";
        public static final String CUSTOM_CUSTOMER_ACCESS_DENIED = "Bu müşterinin verilerine erişim yetkiniz bulunmamaktadır";
        public static final String CUSTOM_COMMENT_ACCESS_DENIED = "Bu yoruma erişim yetkiniz bulunmamaktadır";
        public static final String CUSTOM_INSUFFICIENT_CART_ITEM = "Sepette yeterli ürün bulunmuyor";
        public static final String CUSTOM_FAVORITE_NOT_FOUND = "Favori bulunamadı";
        public static final String CUSTOM_FAVORITE_ACCESS_DENIED = "Bu favoriye erişim yetkiniz bulunmamaktadır";
    }

    public static class Info {
        public static final String VERIFICATION_CODE_TITLE = "Doğrulama Kodu";
        public static final String VERIFICATION_CODE_SENT = "Doğrulama kodu e-posta adresinize gönderildi.";
        public static final String ACTIVATION_EMAIL_TITLE = "Steprella Hesap Aktivasyonu";
    }

    public static class Success {
        public static final String CUSTOM_SUCCESSFULLY = "İşlem Başarıyla Gerçekleşti";
    }
}
