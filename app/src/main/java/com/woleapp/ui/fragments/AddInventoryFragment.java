package com.woleapp.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import com.theartofdev.edmodo.cropper.CropImage;
import com.woleapp.R;
import com.woleapp.adapters.CustomSpinner;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.adapters.ThemeAdapter;
import com.woleapp.databinding.LayoutAddInventoryBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.LocalCache;
import com.woleapp.model.Categories;
import com.woleapp.model.Inventory;
import com.woleapp.model.User;
import com.woleapp.model.BaseResponse;
import com.woleapp.network.MerchantsApiClient;
import com.woleapp.network.MerchantsApiService;
import com.woleapp.util.ConnectivityReceiver;
import com.woleapp.util.Constants;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;
import com.woleapp.util.UtilsAndExtensionsKt;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static com.woleapp.util.UtilsAndExtensionsKt.encodeImage;
import static com.woleapp.util.UtilsAndExtensionsKt.getColorFromColorList;
import static com.woleapp.util.UtilsAndExtensionsKt.loadImageWithGlide;
import static com.woleapp.util.UtilsAndExtensionsKt.setDecodedImageToImageView;

//import uk.co.deanwild.flowtextview.FlowTextView;


public class AddInventoryFragment extends BaseMarketplaceFragment implements View.OnClickListener, Constants {
    Context context;
    private LayoutAddInventoryBinding binding;

    User user;
    Drawable customErrorDrawable;

    String image_path = "";
    Utilities utilities;
    List<Categories> categoriesList = new ArrayList<>();
    List<String> categoriesNameList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private Inventory inventory;
    private Boolean isUpdate = false;

    public static AddInventoryFragment NewInstance(Inventory inventory) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("inventory", Parcels.wrap(inventory));
        bundle.putBoolean("isUpdate", true);
        AddInventoryFragment addInventoryFragment = new AddInventoryFragment();
        addInventoryFragment.setArguments(bundle);
        return addInventoryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        user = SharedPrefManager.getUser();
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //binding.spnCategory.setPrompt(context.getResources().getString(R.string.hint_select_category));
        //getCategoryList();
        Bundle args = getArguments();
        if (args != null && args.containsKey("inventory")) {
            inventory = Parcels.unwrap(args.getParcelable("inventory"));
            if (inventory != null) {
                binding.etProductName.setText(inventory.getProduct_name());
                binding.etPrice.setText(inventory.getPrice());
                binding.etNote.setText(inventory.getDescription());
                UtilsAndExtensionsKt.setDecodedImageToImageView(inventory.getImage_path(), binding.ivProduct);
                if (inventory.getSize() != null)
                    binding.etSize.setText(inventory.getSize());
                isUpdate = true;
                if (URLUtil.isValidUrl(inventory.getImage_path()))
                    loadImageWithGlide(inventory.getImage_path(), binding.ivProduct);
                else
                    setDecodedImageToImageView(inventory.getImage_path(), binding.ivProduct);
                binding.ivProduct.setVisibility(View.VISIBLE);
                Timber.e("isUpdate: "+isUpdate);
            }

        }
        setListeners();

        setSpinner();
    }

    public void replaceInvalidPaths() {
        ///storage/emulated/0/Android/data/com.woleapp/files/inventory_pictures/INVENTORY_1568706790490.jpg
        LocalCache localCache = Injection.provideCacheForInventory(context);


        //localCache.updateImagePath("/storage/emulated/0/Android/data/com.woleapp/files/inventory_pictures/INVENTORY_1565244645146.jpg","cropped");
        localCache.updateImagePath("storage/emulated/0/Android/data/com.woleapp/files/inventory_pictures/INVENTORY_1568706790490.jpg", "1500006790490");
    }

    public void setSpinner() {
        List<com.woleapp.model.Color> colors = new ArrayList<>();
        colors.add(new com.woleapp.model.Color("Amaranth", "#E52B50"));
        colors.add(new com.woleapp.model.Color("Amber", "#FFBF00"));
        colors.add(new com.woleapp.model.Color("Amethyst", "#9966CC"));
        colors.add(new com.woleapp.model.Color("Apricot", "#FBCEB1"));
        colors.add(new com.woleapp.model.Color("Aquamarine", "#7FFFD4"));
        colors.add(new com.woleapp.model.Color("Azure", "#007FFF"));
        colors.add(new com.woleapp.model.Color("Baby Blue", "#89CFF0"));
        colors.add(new com.woleapp.model.Color("Beige", "#F5F5DC"));
        colors.add(new com.woleapp.model.Color("Black", "#000000"));
        colors.add(new com.woleapp.model.Color("Blue", "#0000FF"));
        colors.add(new com.woleapp.model.Color("Blue-green", "#0095B6"));
        colors.add(new com.woleapp.model.Color("Blue-violet", "#8A2BE2"));
        colors.add(new com.woleapp.model.Color("Blush", "#DE5D83"));
        colors.add(new com.woleapp.model.Color("Bronze", "#CD7F32"));
        colors.add(new com.woleapp.model.Color("Carmine", "#960018"));
        colors.add(new com.woleapp.model.Color("Cerise", "#DE3163"));
        colors.add(new com.woleapp.model.Color("Chocolate", "#7B3F00"));
        colors.add(new com.woleapp.model.Color("Chartreuse green", "#7FFF00"));
        colors.add(new com.woleapp.model.Color("Champagne", "#F7E7CE"));

        CustomSpinner customSpinnerAdapter = new CustomSpinner(context, R.layout.spinner_view_color, colors);
        //spinner_view_color
        //binding.spnColor.setAdapter(customSpinnerAdapter);

        binding.spnColor.setAdapter(new NothingSelectedSpinnerAdapter(
                customSpinnerAdapter,
                R.layout.spinner_view_color,
                // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                getActivity()));

        if (inventory != null && inventory.getColor() != null) {
            binding.spnColor.setSelection(getColorFromColorList(colors, inventory.getColor()));
        }
        if (inventory != null)
            binding.spnQuantity.setText(inventory.getQuantity().toString());

    }


    public void setCategoryAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.quantity, R.layout.spinner_view_quantity);
        adapter.setDropDownViewResource(R.layout.item_drop_down_quantity);
        //ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoriesNameList);
        // adapter1.setDropDownViewResource(R.layout.item_drop_down_category);
        binding.spnCategory.setPrompt(context.getResources().getString(R.string.hint_select_category));

        binding.spnCategory.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_category,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        getActivity()));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_add_inventory, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
    }

    public void setListeners() {
        binding.btnUpload.setOnClickListener(this);
        binding.etNote.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.etNote.setRawInputType(InputType.TYPE_CLASS_TEXT);

        binding.btnContinue.setOnClickListener(this);
        RxTextView.textChangeEvents(this.binding.etPrice)
                .skip(1)
                .debounce(400, TimeUnit.MILLISECONDS)
                //.toFlowable(BackpressureStrategy.BUFFER)
                .cache()
                .filter(textViewTextChangeEvent -> this.binding.etPrice.hasFocus())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextViewTextChangeEvent>() {
                    @Override
                    public void accept(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {

                        String text = textViewTextChangeEvent.getText().toString();
                        int len = text.length();
                        if (len > 2) {
                            boolean result = priceValidation(text);
                            if (!result) {
                                //binding.etPrice.setError("Invalid input");
                                binding.etPrice.setError("Invalid input", customErrorDrawable);
                            } else {
                                binding.etPrice.setError(null);
                            }
                        } else if (len > 1) {
                            char firstChar = text.charAt(0);
                            int firstDigit = firstChar;

                            char secondChar = text.charAt(1);
                            int secondDigit = secondChar;

                            if (firstDigit == ASCII_VALUE_OF_ZERO && secondDigit != ASCII_VALUE_OF_POINT) {
                                //text.substring(0,1);
                                //s.replace(1,2,"");
                                binding.etPrice.setText(text.substring(0, 1));
                                //binding.etPrice.setSelection(s.length());
                            } else if (firstDigit == ASCII_VALUE_OF_POINT && secondDigit == ASCII_VALUE_OF_POINT) {
//                                s.replace(1,2,"");
//                                text.substring(0,1);
                                binding.etPrice.setText(text.substring(1, 2));
                                //binding.etPrice.setSelection(s.length());
                        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        {
                            IntStream stream = s.chars();
                            long l = stream.filter(p -> p==ASCII_VALUE_OF_POINT).count();
                            if(l>1)
                            {
                                //s.re
                            }
                            *//*for(Integer i : stream.)
                            {

                            }*//*
                        }*/
                            }
                        }

                        /*String ss = MyViewHolder.this.binding.tvQuestion.getId()+"--"+MyViewHolder.this.binding.tvQuestion.getText().toString();
                        Log.e("ET:-",ss);
                        String text = textViewTextChangeEvent.text().toString();
                        questionList.get(getAdapterPosition()).setQuestion(text);*/
                    }

                    /*@Override
                    public void call(TextViewTextChangeEvent textViewTextChangeEvent)
                    {

                        *//*String ss = MyViewHolder.this.binding.tvQuestion.getId()+"--"+MyViewHolder.this.binding.tvQuestion.getText().toString();
                        Log.e("ET:-",ss);
                        String text = textViewTextChangeEvent.text().toString();
                        questionList.get(getAdapterPosition()).setQuestion(text);*//*
                    }*/
                });
    }


    public boolean priceValidation(String price) {
        //^[1-9][0-9]{12,16}$ (old regular expression)
//		String regex = "^[+][0-9]{12,16}$";
        String regex = "[+-]?([0-9]*[.])?[0-9]+";//"[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";
        //String regex = "^[+][0-9]{10,13}$";
        Pattern numberPattern = Pattern.compile(regex);
        boolean result = numberPattern.matcher(price).matches();
        Log.e("Result: ", result + "--");
        return result;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        super.onPrepareOptionsMenu(menu);
        menu.clear();

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_fragment, menu);
//        MenuItem searchMenu = menu.findItem(R.id.action_edit);
//        searchMenu.setVisible(true);
        //MenuItem delete = menu.findItem(R.id.action_delete);
//        if(showDeleteMenu)
//        {
//            delete.setVisible(true);
//        }
//        else
//        {
//            delete.setVisible(false);
//        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            /*case R.id.action_edit:
                mDataBinding.btnUpdate.setVisibility(View.VISIBLE);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                image_path = resultUri.getPath();
                Log.e("path", "path" + image_path);
                Log.e("resultUri", resultUri.toString());

                binding.ivProduct.setImageURI(resultUri);
                binding.ivProduct.setVisibility(View.VISIBLE);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("Error", error.toString());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v == binding.btnUpload) {
            CropImage.activity().setFixAspectRatio(true)
                    .setAspectRatio(2, 1) //16:9
                    .start(context, this);
        } else if (v == binding.btnContinue) {
            hideKeyBoard();
            validateInputsAndProceed();
        }
    }

    public void hideKeyBoard() {
        try {
            if (context != null) {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

                // check if no view has focus:
                View v = ((Activity) context).getCurrentFocus();
                if (v == null) return;

                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void showProgressDialog() {
//        mProgressDialog = ProgressDialog.show(getActivity(), null, null);
//        mProgressDialog.setContentView(R.layout.dialog_progress);
//        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        mProgressDialog.setCancelable(false);
//    }
//
//    private void hideProgressDialog() {
//        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.dismiss();
//        }
//    }


    public void validateInputsAndProceed() {
        String product_name = binding.etProductName.getText().toString().trim();
        String price = binding.etPrice.getText().toString().trim();
        int selected_quantity = 0;
        try {
            selected_quantity = Integer.parseInt(binding.spnQuantity.getText().toString());
        }catch (Exception exception){
            Timber.e(exception);
        }
        int selected_category = binding.spnCategory.getSelectedItemPosition();
        int selectedColor = binding.spnColor.getSelectedItemPosition();
//        int selected_color = binding.spnColor.getSelectedItemPosition();
        String note = binding.etNote.getText().toString().trim();
//        String size = binding.etSize.getText().toString();
        if (TextUtils.isEmpty(product_name)) {
            binding.etProductName.setError("Product name is required", customErrorDrawable);
            binding.etProductName.requestFocus();
        } /*else if (selected_category <= 0) {
            TextView errorText = binding.spnCategory.getRootView().findViewById(android.R.id.text2);//text2//android.R.id.text1
            errorText.setError("Category  is required", customErrorDrawable);
            //errorText.performClick();
            //errorText.requestFocus();

            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Category is required");//changes the selected item text to this

        }*/ else if (TextUtils.isEmpty(price)) {
            binding.etPrice.setError("Price is required", customErrorDrawable);
            binding.etPrice.requestFocus();
        } else if (!priceValidation(price)) {
            binding.etPrice.setError("Invalid input", customErrorDrawable);
            binding.etPrice.requestFocus();
        } else if (selected_quantity <= 0) {
            binding.spnQuantity.setError("Quantity  required", customErrorDrawable);
            binding.spnQuantity.setTextColor(Color.RED);//just to highlight that this is an error
            //errorText1.setText("Quantity required");//changes the selected item text to this

        }

//        else if(TextUtils.isEmpty(size))
//        {
//            binding.etSize.setError("Size is required",customErrorDrawable);
//            binding.etSize.requestFocus();
//        }
//        else if(selected_color<=0)
//        {
//            TextView errorText = binding.spnColor.getRootView().findViewById(R.id.text2);//android.R.id.text1
//            errorText.setError("Color  is required",customErrorDrawable);
//
//            //errorText.setTextColor(Color.RED);//just to highlight that this is an error
//            //errorText.setText("Quantity required");//changes the selected item text to this
//
//        }
        else if (TextUtils.isEmpty(note)) {
            binding.etNote.setError("A brief description will help in identifying an inventory", customErrorDrawable);
            binding.etNote.requestFocus();
        } else if (TextUtils.isEmpty(image_path) && !isUpdate) {
            binding.btnUpload.setError("Inventory image is required", customErrorDrawable);
        } else {
            if (ConnectivityReceiver.isConnected()) {
                NothingSelectedSpinnerAdapter adapter = (NothingSelectedSpinnerAdapter) binding.spnColor.getAdapter();
//                com.woleapp.model.Color color = (com.woleapp.model.Color) adapter.getSelectedItem(binding.spnCategory.getSelectedItemPosition());
                //binding.spnColor.getAdapter().getItem(binding.spnCategory.getSelectedItemPosition());
            /*Inventory inventory = new Inventory();
            inventory.setUser_id(user.getUser_id());
            inventory.setProduct_name(product_name);
            inventory.setCategory_name(binding.spnCategory.getSelectedItem().toString());
            inventory.setColor_code(color.getColor_code());
            inventory.setColor_name(color.getName());
            inventory.setDescription(note);
            inventory.setPrice(Float.parseFloat(price));
            inventory.setQuantity(Integer.parseInt(binding.spnQuantity.getSelectedItem().toString()));
            inventory.setInsertion_time(Calendar.getInstance().getTimeInMillis());
            inventory.setImage_path(image_path);*/
                if (TextUtils.isEmpty(image_path)){
                    setValues(product_name, note, price, null);
                    return;
                }
                String extension = UtilsAndExtensionsKt.getExtension(image_path);
                Log.e("extension", extension + "--");
                String inventory_path = UtilsAndExtensionsKt.getFilePath(requireActivity(), product_name, extension);
                Log.e("inventory_img_path", inventory_path + "--");
                File sourceFile = new File(image_path);
                final Observable<MultipartBody.Part> encoderObservable = new Compressor(requireContext())
                        .setQuality(50)
                        .compressToFileAsFlowable(sourceFile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toObservable()
                        .flatMap(file -> UtilsAndExtensionsKt.saveAndGetMultipart("product_image",file, new File(inventory_path)));
                Single.fromObservable(encoderObservable)
                        .subscribe(new SingleObserver<MultipartBody.Part>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                showProgressDialog();
                            }

                            @Override
                            public void onSuccess(MultipartBody.Part s) {
                                setValues(product_name, note, price, s);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("tag", e.getLocalizedMessage());
                                Toast.makeText(requireContext(), "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                hideProgressDialog();
                            }
                        });
                //return true;
            } else {
                utilities.showDialogNoNetwork("You need an active internet connection to proceed. Would you like to enable it ?", getActivity());
            }
        }
        //return false;
    }

    private void resetFields() {
        binding.etNote.setText("");
        binding.etPrice.setText("");
        binding.spnCategory.setSelection(0);
        binding.etSize.setText("");
        binding.etProductName.setText("");
        binding.spnColor.setSelection(0);
        binding.ivProduct.setImageDrawable(null);
        binding.ivProduct.setVisibility(View.GONE);
    }

    private void setValues(String product_name, String note, String price, MultipartBody.Part s) {
        if (inventory == null)
            inventory = new Inventory();
        inventory.setProduct_name(product_name);
        inventory.setCategory_name("No category for now");
        inventory.setDescription(note);
        /*inventory.setPrice(Float.parseFloat(price));*/
        inventory.setPrice(price);
        if (binding.spnColor.getSelectedItemPosition() > 0)
            inventory.setColor(((com.woleapp.model.Color) binding.spnColor.getSelectedItem()).getName());
        binding.etSize.getText().toString();
        if (!binding.etSize.getText().toString().isEmpty())
            inventory.setSize(binding.etSize.getText().toString());
        inventory.setQuantity(Integer.parseInt(binding.spnQuantity.getText().toString()));
        //inventory.setInsertion_time(Calendar.getInstance().getTimeInMillis());
        //inventory.setImage_path(isImageAvailable?image_path:"");
        //inventory.setInventory_id(42);
        Log.e("iv", new Gson().toJson(inventory));
        //updateInv(inventory);
        uploadInventory(inventory, s);
    }

    private void uploadInventory(Inventory inventory, MultipartBody.Part image) {
        String merchantId = SharedPrefManager.getUser().getNetplus_id();
        MerchantsApiService apiService = MerchantsApiClient.getMerchantApiService(requireContext());
        Single<BaseResponse<Object>> request;
        if (isUpdate)
            request = apiService.updateProduct(merchantId, inventory.getProductId(), inventory.toPartMap(), image);
        else
            request = apiService.postProduct(merchantId, inventory.toPartMap(), image);
        request.subscribeOn(Schedulers.io())
                .doFinally(() -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(this::hideProgressDialog)
                .subscribe(new SingleObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponse<Object> objectBaseResponse) {
                        Log.e("tag", objectBaseResponse.toString());
                        //Toast.makeText(context, "Item successfully added to inventory", Toast.LENGTH_SHORT).show();
                        //saveInventoryToDB(inventory);
                        String message;
                        if (isUpdate)
                            message = "Product updated successfully";
                        else
                            message = "Item added to inventory";
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        resetFields();
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        hideProgressDialog();
                        Toast.makeText(requireContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void saveInventoryToDB(Inventory inventory) {
        LocalCache localCache = Injection.provideCacheForInventory(context);
        localCache.insertInventoryInfo(inventory, new LocalCache.InsertCallback() {
            @Override
            public void insertFinished(Long result) {
                if (result > 0) {
                    Log.e("Inventory_Saved", result + "--");
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            hideProgressDialog();
                            Toast.makeText(getActivity(), "Inventory saved successfully", Toast.LENGTH_LONG).show();
                        }
                    });
                    getActivity().getSupportFragmentManager().popBackStack();

                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(context, "Failed to update your information. Please try again later.", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private OnItemClickListener.OnItemClickCallback onClick = (view, position) -> {

    };


    public void showPopupTheme() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("Choose a color");
        View dialogView = inflater.inflate(R.layout.list_item_theme, null);
        String[] str = new String[]{"Amaranth", "Amber", "Amethyst", "Apricot", "Aquamarine", "Azure",
                "Baby Blue", "Beige", "Black", "Blue", "Blue-green", "Blue-violet", "Blush", "Bronze", "Chocolate"
        };
        List<com.woleapp.model.Color> colors = new ArrayList<>();
        colors.add(new com.woleapp.model.Color("Amaranth", "#E52B50 "));
        colors.add(new com.woleapp.model.Color("Amber", "#FFBF00 "));
        colors.add(new com.woleapp.model.Color("Amethyst", "#9966CC "));
        colors.add(new com.woleapp.model.Color("Apricot", "#FBCEB1 "));
        colors.add(new com.woleapp.model.Color("Aquamarine", "#7FFFD4 "));
        colors.add(new com.woleapp.model.Color("Azure", "#007FFF "));
        colors.add(new com.woleapp.model.Color("Baby Blue", "#89CFF0 "));
        colors.add(new com.woleapp.model.Color("Beige", "#F5F5DC "));
        colors.add(new com.woleapp.model.Color("Black", "#000000 "));
        colors.add(new com.woleapp.model.Color("Blue", "#0000FF "));
        colors.add(new com.woleapp.model.Color("Blue-green", "#0095B6 "));
        colors.add(new com.woleapp.model.Color("Blue-violet", "#8A2BE2 "));
        colors.add(new com.woleapp.model.Color("Blush", "#DE5D83 "));
        colors.add(new com.woleapp.model.Color("Bronze", "#CD7F32 "));
        colors.add(new com.woleapp.model.Color("Chocolate", "#7B3F00 "));
        ThemeAdapter themeAdapter = new ThemeAdapter(context, colors);
        builder.setAdapter(themeAdapter, (dialog, which) -> {

        });
        //builder.setIcon(R.dra)
        builder.setItems(str,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


//                        defaultChooserIndex = which;
//                        textView.setText(items[which]);
//                        String chosen_lang = languageMap.get(items[which]);
//                        if(!app_language.equals(chosen_lang))
//                        {
//                            defaultChooserIndex = which;
//                            app_language = chosen_lang;
//                            tvLang.setText(items[which]);
//                            changeAppLanguage(chosen_lang);
//
//                        }


                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                if(condition3==1)
//                {
//
//                    etVehicleType.requestFocus();
//                    InputMethodManager imm = (InputMethodManager) StaticData.baseActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(etVehicleType, InputMethodManager.SHOW_IMPLICIT);
//
//                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}