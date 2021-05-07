package edu.upc.ac.jorge.IMSensorLab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public  class DrawView extends View {

    private int radius=0; //500
    private float centerX=1000; //550
    private float centerY=500; //1000
    private int verticalAxisOval=radius;

    // we draw three vectors and one point
    private float vectorPaintX[]={1f,0f,0f};
    private float vectorPaintY[]= {0f,1f,0f};
    private float vectorPaintZ[]= {0f,0f,1f};
    private float vectorPoint[]={0f,0.5f,-0.5f};

    // in screen... if we do not use perspective is not really needed
    private float pixelE[]={0f,0f};
    private float pixelN[]={0f,0f};
    private float pixelZ[]={0f,0f};
    private float pixelPoint[]={0f,0f};

    private Color colPaint;

    private Paint paint = new Paint();

    public DrawView(Context context) {
        super(context);
        paint.setColor(Color.WHITE);
    }

    public void setRadius(int r){
        radius=r;
    }

    public void setVerticalAxisOval(int r){
        verticalAxisOval=r;
    }

    public void setCenterXY(float cx, float cy){
        centerX=cx;
        centerY=cy;
    }

    public void setVectorsXYZ(float x0, float x1, float x2, float y0, float y1, float y2, float z0, float z1, float z2){
        // x and y are in SENSOR device coordenates, vectorPAints i SCREEN coodinates
        vectorPaintX[0] = x0;vectorPaintX[1] = x1;vectorPaintX[2] = x2;  // EAST to x device
        vectorPaintY[0] = y0;vectorPaintY[1] = y1;vectorPaintY[2] = y2; // NORTH to -z device
        vectorPaintZ[0] = z0;vectorPaintZ[1] = z1;vectorPaintZ[2] = z2;

    }

    public void setPoint(float x0, float x1, float x2){
        vectorPoint[0]=x0;
        vectorPoint[1]=x1;
        vectorPoint[2]=x2;
    }

    public void setColor(Color col){
        colPaint=col;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);

            /*
            int i;
            String[] acc_val= new String[3]; arraytovectorstring(acc,acc_val);
            String[] mag_val= new String[3]; arraytovectorstring(mag,mag_val);
            String[] E_val= new String[3]; arraytovectorstring(E,E_val);
            String[] N_val= new String[3]; arraytovectorstring(N,N_val);
            String[] Z_val= new String[3]; arraytovectorstring(Zaxis,Z_val);
            String[] P_val= new String[3]; arraytovectorstring(point_dev,P_val);
            String[] P2_val= new String[3]; arraytovectorstring(point2_dev,P2_val);

            String ACC_reading= "ACC: X:"+ acc_val[0]+"  Y:"+acc_val[1]+"  Z:"+acc_val[2];
            String MAG_reading= "MAG: X:"+ mag_val[0]+"  Y:"+mag_val[1]+"  Z:"+mag_val[2];
            String E_reading= "EAST: X:"+ E_val[0]+"  Y:"+E_val[1]+"  Z:"+E_val[2];
            String N_reading= "NORTH: X:"+ N_val[0]+"  Y:"+N_val[1]+"  Z:"+N_val[2];
            String Z_reading= "VERT: X:"+ Z_val[0]+"  Y:"+Z_val[1]+"  Z:"+Z_val[2];
            String P_reading= "POINT: X:"+ P_val[0]+"  Y:"+P_val[1]+"  Z:"+P_val[2];
            String P2_reading= "POINT2: X:"+ P2_val[0]+"  Y:"+P2_val[1]+"  Z:"+P2_val[2];
            String Deltatime_reading=String.valueOf(time);

            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(0);
            paint.setTextSize(45);
            canvas.drawText(ACC_reading, 10, 50, paint);
            String messageFilter=message;
            if (filterActivate){
                messageFilter=messageFilter+" alpha:"+Float.toString(alpha);
            }
            canvas.drawText(messageFilter, 700, 50, paint);  // message that started activity
            canvas.drawText(MAG_reading, 10, 100, paint);
            if (sig_mag>0){
                canvas.drawText("SIGN = POS", 700, 100, paint);
            } else{
                canvas.drawText("SIGN = NEG", 700, 100, paint);
            }
            paint.setColor(Color.GREEN);
            canvas.drawText(E_reading, 10, 200, paint);
            paint.setColor(Color.RED);
            canvas.drawText(N_reading, 10, 250, paint);
            paint.setColor(Color.BLUE);
            canvas.drawText(Z_reading, 10, 300, paint);
            paint.setColor(Color.RED);
            canvas.drawText(P_reading, 10, 400, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(P2_reading, 10, 450, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(Deltatime_reading, 10, 500, paint);

            */

        paint.setColor(Color.BLACK);    // external frame
        paint.setStrokeWidth(5);
        canvas.drawCircle(centerX,centerY,radius,paint);
        canvas.drawLine(centerX-radius, centerY, centerX+radius, centerY, paint);
        canvas.drawLine(centerX, centerY-radius, centerX, centerY+radius, paint);


        // proyeccion sobre la pantalla... No se usa perspectiva
        // en la pantalla el eje Y tiene signo opuesto al sistem de ref del DEV

        pixelE[0]=vectorPaintX[0]; pixelE[1]= -vectorPaintX[1];
        pixelN[0]=vectorPaintY[0]; pixelN[1]= -vectorPaintY[1];
        pixelZ[0]=vectorPaintZ[0]; pixelZ[1]= -vectorPaintZ[1];
        pixelPoint[0]=vectorPoint[0]; pixelPoint[1]= -vectorPoint[1];


        paint.setStrokeWidth(20);       // normalized EAST
        paint.setColor(Color.GREEN);
        canvas.drawLine(centerX, centerY, pixelE[0]*radius+centerX, pixelE[1]*radius+centerY, paint);

        paint.setStrokeWidth(20);       // normalized NORTH
        paint.setColor(Color.RED);
        canvas.drawLine(centerX, centerY, pixelN[0]*radius+centerX, pixelN[1]*radius+centerY, paint);
        // si queremos dibujar una elipse
        //verticalAxisOval=(int) ((radius * (float) Math.sqrt(pixelN[0]*pixelN[0])) / (float) Math.sqrt(1 - pixelN[1] * pixelN[1]));
        //paint.setStrokeWidth(2);
        //canvas.drawOval(new RectF(centerX-radius, centerY-verticalAxisOval, centerX+radius,centerY+ verticalAxisOval), paint);


        paint.setStrokeWidth(20);       // normalized VERTICAL Z axis
        paint.setColor(Color.BLUE);
        canvas.drawLine(centerX, centerY, pixelZ[0]*radius+centerX, pixelZ[1]*radius+centerY, paint);



        if (vectorPoint[2]<0) {                 // point 1 (Moving in circles)
            paint.setColor(Color.BLACK);
        } else{
            paint.setColor(Color.RED);
        }
        paint.setStrokeWidth(40);
        canvas.drawLine(pixelPoint[0] * radius +centerX , pixelPoint[1] * radius + centerY -20,  pixelPoint[0] * radius + centerX, pixelPoint[1] * radius + centerY+20, paint);


    }

    // if we want to use perspective
    private void setProjectionMatrix(float angleOfView, float near, float far, float[][] M)
    {
        // set the basic projection matrix
        float scale = (float) (1.0f / Math.tan((float)(angleOfView * 0.5f * 3.141592f / 180f)));
        M[0][0] = scale; // scale the x coordinates of the projected point
        M[1][1] = scale; // scale the y coordinates of the projected point
        M[2][2] = -far / (far - near); // used to remap z to [0,1]
        M[3][2] = -far * near / (far - near); // used to remap z [0,1]
        M[2][3] = -1f; // set w = -z
        M[3][3] = 0f;
    }

    void multPointMatrix(float[] in, float[] out, float[][] M)
    {
        //out = in * M;
        out[0]   = in[0] * M[0][0] + in[1] * M[1][0] + in[2] * M[2][0] + /* in.z = 1 */ M[3][0];
        out[1]   = in[0] * M[0][1] + in[1] * M[1][1] + in[2] * M[2][1] + /* in.z = 1 */ M[3][1];
        out[2]   = in[0] * M[0][2] + in[1] * M[1][2] + in[2] * M[2][2] + /* in.z = 1 */ M[3][2];
        float w = in[0] * M[0][3] + in[1] * M[1][3] + in[2] * M[2][3] + /* in.z = 1 */ M[3][3];

        // normalize if w is different than 1 (convert from homogeneous to Cartesian coordinates)
        if (w != 1) {
            out[0] /= w;
            out[1] /= w;
            out[2] /= w;
        }
    }

}

