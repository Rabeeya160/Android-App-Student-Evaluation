package com.jyaconsulting.aashal1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jyaconsulting.aashal1.R;
import com.jyaconsulting.aashal1.SelectedOutletActivity;
import com.jyaconsulting.aashal1.Utils;
import com.jyaconsulting.aashal1.model.PJPModel;
import com.jyaconsulting.aashal1.viewholder.PJPViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PJPAdapter extends RecyclerView.Adapter<PJPViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<PJPModel> data;
    private PJPViewHolder holder;
    private PJPModel current;
    private String OutletName, Contact, Address, Owner, id, status, Day, StartDate, AlterNate,
    OwnerName,OutletAddress,OutletStatus,SDay,AllDay;
    private int global_position;
    private String outletID, outletName, phone;

    public PJPAdapter(Context context, List<PJPModel> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public PJPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*This Method inflate our adapter Layout*/
        View view = inflater.inflate(R.layout.outlet_layout, parent, false);
        holder = new PJPViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PJPViewHolder holder, int position) {
        /*This method will populate our recylerView with data also it will show position of each item*/
        /*access all your ViewHolder widget here */
//        this.position = position;
        current = data.get(position);
        OutletName = current.getOuletName();
        Contact = current.getPhone();
        Owner = current.getOwnerName();
        Address = current.getAddress();
        id = current.getPjpID();
        status = current.getPjpStatus();
        StartDate = current.getStartDate();
        Day = current.getDay();
        AlterNate = current.getAlterNate();


        holder.outletName.setText(OutletName);
        holder.outletPhone.setText(Contact);
        holder.outletOwner.setText(Owner);
        holder.outletAddress.setText(Address);
        holder.pjpID.setText(id);
        holder.pjpStatus.setText(status);

        holder.outletDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                global_position = holder.getAdapterPosition();
                notifyDataSetChanged();
                /*Order Activity*/

                /*to get data of each item*/
                outletID = data.get(global_position).getPjpID();
                outletName = data.get(global_position).getOuletName();
                OutletAddress = data.get(global_position).getAddress();
                phone = data.get(global_position).getPhone();
                OwnerName = data.get(global_position).getOwnerName();
                OutletStatus = data.get(global_position).getPjpStatus();
               SDay = data.get(global_position).getDay();
                AllDay = data.get(global_position).getAllDays();
//                String SDate = data.get(global_position).getStartDate();
//                String alterNate = data.get(global_position).getAlterNate();

                //TODO =========Get Current Day Name=========
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                Date d = new Date();
                String dayOfTheWeek = sdf.format(d);

//                if (!alterNate.isEmpty()) {
//                    Date today = new Date();
//                    SimpleDateFormat date = new SimpleDateFormat("dd");
//                    String currentDate = date.format(today);
//
//                    SimpleDateFormat month = new SimpleDateFormat("mm");
//                    SimpleDateFormat year = new SimpleDateFormat("yyyy");
//                    String currentMonth = month.format(today);
//                    String currentYear = year.format(today);
//
//                    int sDate = Integer.parseInt(SDate);
//                    int AlterNate = Integer.parseInt(alterNate);
//                    int cDate = Integer.parseInt(currentDate);
////                    long cMonth = Integer.parseInt(currentMonth);
//                    int cYear = Integer.parseInt(currentYear);
//
//                    if (sDate == cDate && status.equalsIgnoreCase("open")) {
//                        showOutlets();
//                    } else if ((cDate - sDate) % AlterNate == 0) {
//                        showOutlets();
//                    } else {
//                        int ComingDate;
//                        ComingDate = cDate + (cDate - sDate) % AlterNate;
//                        switch (currentMonth) {
//                            case "01":
//                            case "03":
//                            case "05":
//                            case "07":
//                            case "08":
//                            case "10":
//                            case "12":
//                                showToast(ComingDate, 31);
//                                break;
//                            case "04":
//                            case "06":
//                            case "09":
//                            case "11":
//                                showToast(ComingDate, 30);
//                                break;
//                            case "02":
//                                if (cYear % 4 == 0) {
//                                    showToast(ComingDate, 29);
//                                } else {
//                                    showToast(ComingDate, 28);
//                                }
//                                break;
//                        }
//
//                        //31 jan mar may july aug oct dec
//                        //30 fab, april, jun, sep, nov
//
//
//                        int comingDate = cDate + (cDate - sDate) % AlterNate;
//                        if (comingDate > 30)
//                            Toast.makeText(context, "Outlet will be visit on " + comingDate, Toast.LENGTH_SHORT).show();
//                    }
//                } else {

                if (AllDay.equalsIgnoreCase("true")) {
                    showOutlets();
                } else {
                    if (!dayOfTheWeek.equalsIgnoreCase(SDay)) {
                        Toast.makeText(context, "Sorry You can take Order\nFor this Outlet Only On\n" + SDay, Toast.LENGTH_LONG)
                                .show();
                    } else {
                        showOutlets();
                    }

                }
            }
        });

    }

//    private void showToast(int ComingDate, int LatDate) {
//        if (ComingDate > 29) {
//            ComingDate = ComingDate - 29;
//            Toast.makeText(context, "You will be visit this outlet on " + ComingDate + " Date", Toast.LENGTH_SHORT)
//                    .show();
//        } else {
//            Toast.makeText(context, "You will be visit this outlet on " + ComingDate + " Date", Toast.LENGTH_SHORT)
//                    .show();
//        }
//    }

    private void showOutlets() {
        if (outletID.equalsIgnoreCase("N/A") || outletName.equalsIgnoreCase("N/A")
                || Address.equalsIgnoreCase("N/A") || phone.equalsIgnoreCase("N/A")
                || Owner.equalsIgnoreCase("N/A")) {
            Toast.makeText(context, "No Outlet Available", Toast.LENGTH_SHORT).show();
        } else {
            if (OutletStatus.equalsIgnoreCase("open")) {

                Intent intent = new Intent(context, SelectedOutletActivity.class);
                /*put each data*/
                Utils.savePreferences("pjpID", outletID, context);
                Utils.savePreferences("outletName", outletName, context);
                Utils.savePreferences("OutletAddress", OutletAddress, context);
                Utils.savePreferences("OutletPhone", phone, context);
                Utils.savePreferences("OwnerName", OwnerName, context);

                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Outlet Is visited", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
