
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
public class Inventory$$Parcelable
    implements Parcelable, ParcelWrapper<com.woleapp.model.Inventory>
{

    private com.woleapp.model.Inventory inventory$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Inventory$$Parcelable>CREATOR = new Creator<Inventory$$Parcelable>() {


        @Override
        public Inventory$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Inventory$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Inventory$$Parcelable[] newArray(int size) {
            return new Inventory$$Parcelable[size] ;
        }

    }
    ;

    public Inventory$$Parcelable(com.woleapp.model.Inventory inventory$$2) {
        inventory$$0 = inventory$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(inventory$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.woleapp.model.Inventory inventory$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(inventory$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(inventory$$1));
            parcel$$1 .writeString(inventory$$1 .getCategory_name());
            if (inventory$$1 .getQuantity() == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeInt(inventory$$1 .getQuantity());
            }
            parcel$$1 .writeString(inventory$$1 .getProductId());
            parcel$$1 .writeString(inventory$$1 .getColor());
            if (inventory$$1 .getInventory_id() == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeInt(inventory$$1 .getInventory_id());
            }
            parcel$$1 .writeString(inventory$$1 .getDescription());
            parcel$$1 .writeString(inventory$$1 .getProduct_name());
            parcel$$1 .writeFloat(inventory$$1 .getDeliveryFee());
            parcel$$1 .writeString(inventory$$1 .getSize());
            parcel$$1 .writeString(inventory$$1 .getMerchantId());
            parcel$$1 .writeString(inventory$$1 .getPrice());
            parcel$$1 .writeString(inventory$$1 .getImage_path());
            parcel$$1 .writeString(inventory$$1 .getStoreName());
            parcel$$1 .writeString(inventory$$1 .getImageLocalPath());
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.woleapp.model.Inventory getParcel() {
        return inventory$$0;
    }

    public static com.woleapp.model.Inventory read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.woleapp.model.Inventory inventory$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            inventory$$4 = new com.woleapp.model.Inventory();
            identityMap$$1 .put(reservation$$0, inventory$$4);
            inventory$$4 .setCategory_name(parcel$$3 .readString());
            int int$$0 = parcel$$3 .readInt();
            java.lang.Integer integer$$0;
            if (int$$0 < 0) {
                integer$$0 = null;
            } else {
                integer$$0 = parcel$$3 .readInt();
            }
            inventory$$4 .setQuantity(integer$$0);
            inventory$$4 .setProductId(parcel$$3 .readString());
            inventory$$4 .setColor(parcel$$3 .readString());
            int int$$1 = parcel$$3 .readInt();
            java.lang.Integer integer$$1;
            if (int$$1 < 0) {
                integer$$1 = null;
            } else {
                integer$$1 = parcel$$3 .readInt();
            }
            inventory$$4 .setInventory_id(integer$$1);
            inventory$$4 .setDescription(parcel$$3 .readString());
            inventory$$4 .setProduct_name(parcel$$3 .readString());
            inventory$$4 .setDeliveryFee(parcel$$3 .readFloat());
            inventory$$4 .setSize(parcel$$3 .readString());
            inventory$$4 .setMerchantId(parcel$$3 .readString());
            inventory$$4 .setPrice(parcel$$3 .readString());
            inventory$$4 .setImage_path(parcel$$3 .readString());
            inventory$$4 .setStoreName(parcel$$3 .readString());
            inventory$$4 .setImageLocalPath(parcel$$3 .readString());
            com.woleapp.model.Inventory inventory$$3 = inventory$$4;
            identityMap$$1 .put(identity$$1, inventory$$3);
            return inventory$$3;
        }
    }

}
