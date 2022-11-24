package com.enyoi.apimesaayuda.security.services.impl;

import com.enyoi.apimesaayuda.security.services.IBuildEmailService;
import org.springframework.stereotype.Service;

@Service
public class BuildEmailService implements IBuildEmailService {
    public static final String DIV = "            </div>\n";

    @Override
    public String buildEmail(String name, String link) {
        return "<div style=\"\n" +
                "    display: grid;\n" +
                "    justify-content: center;\n" +
                "    margin: 20px;\n" +
                "    font-family: Roboto, 'Helvetica Neue', sans-serif;\n" +
                "    color: #0000008a;\">\n" +
                "        <div style=\"\n" +
                "        max-width: 780px; \n" +
                "        padding: 20px; \n" +
                "        border-radius: 20px; \n" +
                "        text-align: center;\n" +
                "        box-shadow: 0px 2px 1px -1px rgb(0 0 0 / 20%), \n" +
                "                    0px 1px 1px 0px rgb(0 0 0 / 14%), \n" +
                "                    0px 1px 3px 0px rgb(0 0 0 / 12%);\">\n" +
                "            <div style=\"text-align: center;\"> \n" +
                "                <img src=\"https://sinapptic.com/wp-content/uploads/2016/11/helpdesk2-500x200.png\" alt=\"logo\" width=\"500px\">\n" +
                DIV +
                "            <h2 style=\"text-align: center; font-size: 24px;\">Hola, " + name + "</h2>\n" +
                "            <h1 style=\"\n" +
                "            text-align: center; \n" +
                "            color: #0000008a;\n" +
                "            font-size: 36px;\n" +
                "            font-weight: bold;\n" +
                "            line-height: normal;\">Bienvenido a <strong style=\"color: #3344b0;\">Enyoi</strong></h1>\n" +
                "            <div style=\"padding: 20px 0px; font-size: 18px; text-align: justify;\">\n" +
                "                <div>\n" +
                "                    Bienvenido a Enyoi, disfruta tu aventura!\n" +
                "                </div>\n" +
                DIV +
                "            <div style=\"padding-top: 40px; text-align: center;\">\n" +
                "                <a href=\"" + link + "\" target=\"_blank\" style=\"\n" +
                "                color: #3344b0;\n" +
                "                font-weight: bold;\n" +
                "                font-size: 18px;\n" +
                "                text-decoration: none; \n" +
                "                border-radius: 5px; \n" +
                "                border: 1px solid rgba(0, 0, 0, 0.12);\n" +
                "                padding: 10px 20px;\">Activar Cuenta</a>\n" +
                DIV +
                "            <div><br></br><br></br><br></br></div>" +
                "            <div>" +
                "               <strong style=\"color:#000000;font-size:18px\"> " +
                "                       El enlace de confirmación caducará en 15 minutos.\n" +
                "               </strong>" +
                DIV +
                "        </div>\n" +
                "    </div>";
    }
    @Override
    public String recuperarClaveEmail(String name, String link) {
        return "<div style=\"\n" +
                "    display: grid;\n" +
                "    justify-content: center;\n" +
                "    margin: 20px;\n" +
                "    font-family: Roboto, 'Helvetica Neue', sans-serif;\n" +
                "    color: #0000008a;\">\n" +
                "        <div style=\"\n" +
                "        max-width: 780px; \n" +
                "        padding: 20px; \n" +
                "        border-radius: 20px; \n" +
                "        text-align: center;\n" +
                "        box-shadow: 0px 2px 1px -1px rgb(0 0 0 / 20%), \n" +
                "                    0px 1px 1px 0px rgb(0 0 0 / 14%), \n" +
                "                    0px 1px 3px 0px rgb(0 0 0 / 12%);\">\n" +
                "            <div style=\"text-align: center;\"> \n" +
                "                <img src=\"https://sinapptic.com/wp-content/uploads/2016/11/helpdesk2-500x200.png\" alt=\"logo\" width=\"500px\">\n" +
                DIV +
                "            <h2 style=\"text-align: center; font-size: 24px;\">Hola, " + name + "</h2>\n" +
                "            <h1 style=\"\n" +
                "            text-align: center; \n" +
                "            color: #0000008a;\n" +
                "            font-size: 36px;\n" +
                "            font-weight: bold;\n" +
                "            line-height: normal;\">Bienvenido a <strong style=\"color: #3344b0;\">Enyoi</strong></h1>\n" +
                "            <div style=\"padding: 20px 0px; font-size: 18px; text-align: justify;\">\n" +
                "                <div>\n" +
                "                    Bienvenido a Enyoi, disfruta tu aventura!\n" +
                "                </div>\n" +
                DIV +
                "            <div style=\"padding-top: 40px; text-align: center;\">\n" +
                "                <a href=\"" + link + "\" target=\"_blank\" style=\"\n" +
                "                color: #3344b0;\n" +
                "                font-weight: bold;\n" +
                "                font-size: 18px;\n" +
                "                text-decoration: none; \n" +
                "                border-radius: 5px; \n" +
                "                border: 1px solid rgba(0, 0, 0, 0.12);\n" +
                "                padding: 10px 20px;\">Recuperar Clave</a>\n" +
                DIV +
                "            <div><br></br><br></br><br></br></div>" +
                "            <div>" +
                "               <strong style=\"color:#000000;font-size:18px\"> " +
                "                       El enlace de confirmación caducará en 15 minutos.\n" +
                "               </strong>" +
                DIV +
                "        </div>\n" +
                "    </div>";
    }
}
