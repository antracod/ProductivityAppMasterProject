<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
xmlns:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="match_parent"
tools:context=".activities.SettingsActivity">

<ScrollView
android:layout_width="match_parent"
android:layout_height="match_parent">

<LinearLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:orientation="vertical" >

<TextView
android:id="@+id/textView"
style="@style/BoldBlackText"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:layout_marginStart="26dp"
android:layout_marginTop="10dp"
android:layout_marginEnd="20dp"
android:text="Settings"

android:textSize="50sp">


</TextView>

<ImageView
android:id="@+id/settingsImageView"
android:layout_width="128dp"
android:layout_height="128dp"
android:layout_marginTop="20dp"
android:layout_gravity="center_horizontal"
android:src="@drawable/ic_hard_avatar" />


<TextView

android:id="@+id/nameSampleSettingsTextView"
style="@style/RegularGrayText"
android:layout_width="385dp"
android:layout_height="wrap_content"
android:layout_marginStart="24dp"

android:layout_marginTop="26dp"
android:text="Name"
android:textSize="16sp">


</TextView>
<TextView
android:id="@+id/nameValueSettingsTextView"
style="@style/BoldBlackText"
android:layout_width="385dp"
android:layout_height="wrap_content"
android:layout_marginStart="24dp"

android:text="Not logged"
android:textSize="20sp">


</TextView>








<androidx.appcompat.widget.AppCompatButton
android:id="@+id/updateProfileButton"
style="@style/OutlineRoundButton"
android:layout_width="match_parent"
android:layout_height="wrap_content"


android:layout_marginStart="20dp"
android:layout_marginEnd="20dp"
android:layout_marginTop="20dp"
android:padding="20dp"
android:text="Update Profile"
android:textAllCaps="false"
android:textSize="24sp" />
<androidx.appcompat.widget.AppCompatButton
android:id="@+id/enableChatAndExportsButton"
style="@style/OutlineRoundButton"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:layout_marginStart="20dp"
android:layout_marginEnd="20dp"
android:layout_marginTop="10dp"
android:padding="20dp"
android:text="Enable Chat and Exports"
android:textAllCaps="false"
android:textSize="24sp" />

<androidx.appcompat.widget.AppCompatButton
android:id="@+id/logOutButtonSettings"
style="@style/OutlineRoundButton"
android:layout_width="match_parent"
android:layout_height="wrap_content"

android:layout_marginStart="20dp"
android:layout_marginEnd="20dp"
android:layout_marginTop="10dp"


android:padding="20dp"
android:text="Log out"
android:textAllCaps="false"
android:textSize="24sp" />
<androidx.appcompat.widget.AppCompatButton
android:id="@+id/firebaseGetEventsSettingsButton"
style="@style/OutlineRoundButton"
android:layout_width="match_parent"
android:layout_height="wrap_content"

android:layout_marginStart="20dp"
android:layout_marginEnd="20dp"
android:layout_marginTop="10dp"
android:layout_marginBottom="100dp"

android:padding="20dp"
android:text="Sync Friend Events"
android:textAllCaps="false"
android:textSize="24sp" />
</LinearLayout>
</ScrollView>


<com.google.android.material.bottomnavigation.BottomNavigationView
android:id="@+id/bottom_navigator"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:layout_margin="10dp"
android:background="@drawable/full_round_shape"
android:elevation="4dp"
android:padding="10dp"

app:itemIconTint="@drawable/selector"
app:itemTextColor="@drawable/selector"
app:labelVisibilityMode="unlabeled"


app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:menu="@menu/menu"

>


</com.google.android.material.bottomnavigation.BottomNavigationView>




</androidx.constraintlayout.widget.ConstraintLayout>