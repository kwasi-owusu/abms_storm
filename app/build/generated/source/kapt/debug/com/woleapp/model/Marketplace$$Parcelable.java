
package com.woleapp.model;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated("org.parceler.ParcelAnnotationProcessor")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Marketplace$$Parcelable
    implements Parcelable, ParcelWrapper<com.woleapp.model.Marketplace>
{

    private com.woleapp.model.Marketplace marketplace$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Marketplace$$Parcelable>CREATOR = new Creator<Marketplace$$Parcelable>() {


        @Override
        public Marketplace$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Marketplace$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Marketplace$$Parcelable[] newArray(int size) {
            return new Marketplace$$Parcelable[size] ;
        }

    }
    ;

    public Marketplace$$Parcelable(com.woleapp.model.Marketplace marketplace$$2) {
        marketplace$$0 = marketplace$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(marketplace$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.woleapp.model.Marketplace marketplace$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(marketplace$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(marketplace$$1));
            parcel$$1 .writeString(marketplace$$1 .getDeliveryFee());
            parcel$$1 .writeString(marketplace$$1 .getMerchantId());
            parcel$$1 .writeString(marketplace$$1 .getName());
            parcel$$1 .writeString(marketplace$$1 .getDescription());
            parcel$$1 .writeString(marketplace$$1 .getLogo());
            parcel$$1 .writeInt(marketplace$$1 .getId());
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.woleapp.model.Marketplace getParcel() {
        return marketplace$$0;
    }

    public static com.woleapp.model.Marketplace read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.woleapp.model.Marketplace marketplace$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            marketplace$$4 = new com.woleapp.model.Marketplace();
            identityMap$$1 .put(reservation$$0, marketplace$$4);
            marketplace$$4 .setDeliveryFee(parcel$$3 .readString());
            marketplace$$4 .setMerchantId(parcel$$3 .readString());
            marketplace$$4 .setName(parcel$$3 .readString());
            marketplace$$4 .setDescription(parcel$$3 .readString());
            marketplace$$4 .setLogo(parcel$$3 .readString());
            marketplace$$4 .setId(parcel$$3 .readInt());
            com.woleapp.model.Marketplace marketplace$$3 = marketplace$$4;
            identityMap$$1 .put(identity$$1, marketplace$$3);
            return marketplace$$3;
        }
    }

}
