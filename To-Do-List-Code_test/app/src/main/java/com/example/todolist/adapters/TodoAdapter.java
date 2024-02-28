package com.example.todolist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.database.AppSharedPreferences;
import com.example.todolist.model.TodoItem;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    Context context;
    ArrayList<TodoItem> todoItems;

    public TodoAdapter(Context context, ArrayList<TodoItem> todoItems) {
        this.context = context;
        this.todoItems = todoItems;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.bind(todoItems.get(position),position);
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    public void addItem(TodoItem todoItem) {
        todoItems.add(todoItem);
        // Notify adapter that an item has been added
        notifyItemInserted(todoItems.size() - 1);
    }

    // Existing code...

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView todoItemTextView;
        CheckBox todoCheckBox;
        ImageView todoRemoveItem;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoItemTextView = itemView.findViewById(R.id.todoItemTextView);
            todoCheckBox = itemView.findViewById(R.id.todoCheckBox);
            todoRemoveItem = itemView.findViewById(R.id.deleteIcon);
        }

        public void bind(TodoItem todoItem,int position) {
            todoItemTextView.setText(todoItem.getTitle());
            todoCheckBox.setChecked(todoItem.isCompleted());
            itemView.setActivated(todoItem.isCompleted());
            // Here you can set a listener for the CheckBox
            todoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Handle the checked state change here
                    // For example, you can update the task as completed in your data structure
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Update the selected state of the item in the adapter
                        todoItems.get(position).setCompleted(isChecked);
                        itemView.setActivated(isChecked);
                        AppSharedPreferences.getInstance(context).saveHashMap(todoItem.getId(),todoItems.get(position));

                    }

                }
            });

            todoRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Remove the item from the list
                    todoItems.remove(todoItem);
                    // Notify adapter about the item removal
                    notifyItemRemoved(position);
                    AppSharedPreferences.getInstance(context).removeItemFromHashMap(todoItem.getId());

                }
            });
        }
    }
}
