package com.codedojo.firechat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedojo.firechat.R;
import com.codedojo.firechat.model.ModelChat;

import java.util.List;

/**
 * Created by jonesrandom on 12/26/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

public class AdapterChat extends RecyclerView.Adapter<HolderChat> {

    private List<ModelChat> dataChat;

    public AdapterChat(List<ModelChat> dataChat) {
        this.dataChat = dataChat;
    }

    @Override
    public HolderChat onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat , parent , false);
        return new HolderChat(v);
    }

    @Override
    public void onBindViewHolder(HolderChat holder, int position) {
        holder.setContent(dataChat.get(position));
    }

    @Override
    public int getItemCount() {
        return dataChat.size();
    }
}
