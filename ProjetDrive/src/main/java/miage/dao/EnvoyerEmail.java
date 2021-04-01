/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.dao;

import miage.metier.Client;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Test de JavaMail et Apache Commons Mail.
 */
public class EnvoyerEmail {

    /**
     * Propriétés.
     */

    private static String SERVEUR_SMTP = "smtp.ut-capitole.fr";
    private static String LOGIN = "21809051";
    private static String FROM = "siyvan.zhou@ut-capitole.fr";
    private static String MDP = "mdp";

    public static void envoyerEmail(int idCli) {  //int idCli,int idCmd
        /**
         * Envoi un mail simple avec un fichier attaché.
         */
        Client c =new Client();
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
               Transaction t = session.beginTransaction();
                c = session.get(Client.class, idCli);
            }
        /*----- Create the attachment -----*/
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("src\\facturation.pdf");
        attachment.setDisposition(EmailAttachment.ATTACHMENT);

        /*----- Create the email message -----*/
        MultiPartEmail email_attach = new MultiPartEmail();
        email_attach.setHostName(SERVEUR_SMTP);
        email_attach.setSmtpPort(465);
        email_attach.setAuthentication(LOGIN, MDP);
        email_attach.setSSLOnConnect(true);

        try {
            email_attach.setFrom(FROM);
            email_attach.addTo(c.getEmailCli());
            email_attach.setSubject("JavaMail attachment");
            email_attach.setMsg("Cet email contient un ficher attaché !");
            email_attach.attach(attachment);
            email_attach.send();
            System.out.println("Mail avec un fichier attaché envoyé !");
        } catch (EmailException ex) {
            System.out.println("Erreur lors de l'envoi d'un mail avec un fichier attaché !\n" + ex.getMessage());
        }
//        System.out.println(c.getEmailCli());
    }

    /**
     * Programme principal.
     */
    public static void main(String[] args) {
//        envoyerEmail(2);
    }

} /*----- Fin de TpMail -----*/
