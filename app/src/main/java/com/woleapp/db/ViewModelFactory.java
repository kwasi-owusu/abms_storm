package com.woleapp.db;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.woleapp.viewmodels.InventoryViewModel;

import java.lang.reflect.InvocationTargetException;

/**
 * Factory for ViewModels
 *
 * @author Kaushik N Sanji
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
    {
        if (UserViewModel.class.isAssignableFrom(modelClass))
        {
            try
            {
                return modelClass.getConstructor(Repository.class).newInstance(repository);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
        else if (InventoryViewModel.class.isAssignableFrom(modelClass))
        {
            try
            {
                return modelClass.getConstructor(Repository.class).newInstance(repository);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
        throw new IllegalArgumentException("Unknown ViewModel class " + modelClass);
    }
}
