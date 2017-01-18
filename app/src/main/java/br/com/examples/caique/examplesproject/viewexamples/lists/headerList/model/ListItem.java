package br.com.examples.caique.examplesproject.viewexamples.lists.headerList.model;

/**
 * Created by CBertan on 21/12/2016.
 */

public interface ListItem {
    public int VT_HEADER = 0;
    public int VT_ITEM = 1;

    int getType();
}
//public abstract class ListItem extends GenericEntity {
//    public static final int VT_HEADER = 0;
//    public static final int VT_ITEM = 1;
//
//    abstract public int getType();
//}
