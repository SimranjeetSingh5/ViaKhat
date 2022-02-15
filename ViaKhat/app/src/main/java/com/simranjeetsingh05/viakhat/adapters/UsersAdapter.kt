package com.simranjeetsingh05.viakhat.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simranjeetsingh05.viakhat.databinding.ItemContainerUserBinding
import com.simranjeetsingh05.viakhat.models.User
import com.simranjeetsingh05.viakhat.utilities.Constants

class UsersAdapter(val users: List<User>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {


    class UserViewHolder(itemContainerUserBinding: ItemContainerUserBinding) :
        RecyclerView.ViewHolder(itemContainerUserBinding.root) {
        private val binding:ItemContainerUserBinding = itemContainerUserBinding

        fun setUserDetail(user:User){
            binding.textName.text = user.name
            binding.textName.text = user.email
            binding.imageProfile.setImageBitmap(getUserImage(user.image))
        }
        private fun getUserImage(encodedImage:String):Bitmap{
            val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemContainerUserBinding:ItemContainerUserBinding = ItemContainerUserBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(itemContainerUserBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setUserDetail(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

}