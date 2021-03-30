package miage.dao;


import com.spire.barcode.*;
import com.spire.pdf.*;
import com.spire.pdf.barcode.*;
import com.spire.pdf.graphics.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import static miage.dao.EnvoyerEmail.envoyerEmail;
import miage.metier.LigneCommande;
import static miage.dao.TestHibernate.chercherCommandeClient;

public class PDFEnsemble {
     public static void imprimerFacturation(int idCli,int idCmd) {
       // Un nouveau PDF document
        PdfDocument pdf = new PdfDocument();
        // Ajouter un nouveau page pour le document
        PdfPageBase page = pdf.getPages().add();
        // Initialisation Y
        double y = 30;
        // Fond de texte
        PdfFont font = new PdfFont(PdfFontFamily.Helvetica, 12, PdfFontStyle.Bold);
        // Ajouter une texte
        PdfTextWidget text = new PdfTextWidget();
        text.setFont(font);
        
        text.setText("                                                                  Facturation");
        PdfLayoutResult result = text.draw(page, 0, y);
        page = result.getPage();
        
        java.util.List<LigneCommande> lproduit =chercherCommandeClient(idCli,idCmd);  //parametre
        
        text.setText("Client:"+lproduit.get(0).getCommandes().getClientCmd().getPrenomCli()+" "+lproduit.get(0).getCommandes().getClientCmd().getNomCli());
        result = text.draw(page, 50, y+y);
        page = result.getPage();
        
        text.setText("Produit                   Quantite                    PrixUnitaire");
        result = text.draw(page, 50, y+y+y+y);
        page = result.getPage();

        for(int i=0;i<lproduit.size();i++){        
            text.setText(lproduit.get(i).getProduits().getLibelleP()+"                  "+lproduit.get(i).getQteCP()+"                              "+lproduit.get(i).getProduits().getPrixUnitaireP());
            result = text.draw(page, 50, (y*i)+y+y+y+y+y);
            page = result.getPage();        
        }
        
        // Generation de la contenu de QRcode
        BarcodeSettings settings = new BarcodeSettings();//创建二维码图形
        settings.setType(BarCodeType.QR_Code);
        settings.setData(lproduit.get(0).getCommandes().getClientCmd().getPrenomCli()+" "+lproduit.get(0).getCommandes().getClientCmd().getNomCli());
        settings.setData2D("QRCode pour le retrait");
        settings.setX(1f);
        settings.setLeftMargin(0);
        settings.setShowTextOnBottom(true);
        settings.setQRCodeECL(QRCodeECL.Q);
        settings.setQRCodeDataMode(QRCodeDataMode.Numeric);
        
        // Ajouter le QRcode dans PDF ducument
        BarCodeGenerator generator = new BarCodeGenerator(settings);
        Image image = generator.generateImage();
        PdfImage pdfImage = PdfImage.fromImage((BufferedImage) image);//绘制二维码图片到PDF
        y = result.getBounds().getY() + result.getBounds().getHeight() + 2;
        page.getCanvas().drawImage(pdfImage, 100, 550);
        
        //Enregistrer le fichier PDF
        pdf.saveToFile("src\\facturation.pdf");
        pdf.dispose();
        
        //envoyerEmail(idCli);
     
     }

    public static void main(String[] args) {

   imprimerFacturation(2,36);
    }

}
