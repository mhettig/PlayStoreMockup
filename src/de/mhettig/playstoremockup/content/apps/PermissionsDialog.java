package de.mhettig.playstoremockup.content.apps;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.mhettig.playstoremockup.R;
import de.mhettig.playstoremockup.main.Handler;
import de.mhettig.playstoremockup.model.App;
import de.mhettig.playstoremockup.model.Permission;

//TODO on start Holo.Light theme is set with a little delay
public class PermissionsDialog extends DialogFragment {
	
	private App app;
	private AppDetailActivity context;

	
	AsyncTask<Void, Bitmap, Void> loopPictures = null;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		//get app
		app = Handler.getApp(getArguments().getInt("appID"));
		
		//set context
		context = (AppDetailActivity) getActivity();
		
		//init dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_permissions, null);		
		
		TextView tvSubtitle = (TextView) view.findViewById(R.id.perm_subtitle);
		Button button = (Button) view.findViewById(R.id.perm_btn_accept);

		if (app.getPermissions().length == 0) {
			tvSubtitle.setText(Html.fromHtml("<i><b>" + app.getName() + "</b> does not require any special permissions</i>"));
			tvSubtitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
			button.setText("CONTINUE");
		} else {			
			tvSubtitle.setText(Html.fromHtml("<b>" + app.getName() + "</b> needs access to:"));
		}
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getDialog().dismiss();
				context.startFakeInstallation();
			}
		});
		
		initPermissions(view, inflater);
		
		builder.setView(view);
		return builder.create();
	}
	
	@Override
	public void onPause() {
		super.onPause();		
		this.dismiss();
	}

	private void initPermissions(View view, LayoutInflater inflater) {
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.perm_ll);
				
		for (Permission permission : app.getPermissions()) {
			
			View row = inflater.inflate(R.layout.permission_row, null);
			TextView tvTitle = (TextView) row.findViewById(R.id.perm_std_title);
			TextView tvDesc = (TextView) row.findViewById(R.id.perm_std_desc);
											
			tvTitle.setText(permission.getCaption());
			tvDesc.setText(permission.getDescription());
				
			layout.addView(row);
		}
	}
}
