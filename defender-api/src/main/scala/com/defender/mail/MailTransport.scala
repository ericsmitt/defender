package com.defender.mail

object MailTransport {
  def send(subject: String, message: String): Unit = {
    //    val props = new Properties
    //    Configuration.Mail.Entries.foreach({ case (k, v) => props.put(k, v) })
    //    val session = Session.getInstance(
    //      props,
    //      new Authenticator {
    //        override def getPasswordAuthentication = new PasswordAuthentication(
    //          Configuration.Mail.Username,
    //          Configuration.Mail.Password
    //        )
    //      }
    //    )
    //    val msg = new MimeMessage(session)
    //    msg.setFrom(new InternetAddress(Configuration.Mail.From))
    //    msg.setRecipients(
    //      Message.RecipientType.TO,
    //      InternetAddress.parse(Configuration.Mail.To).asInstanceOf[Array[Address]]
    //    )
    //    msg.setSubject(subject)
    //    msg.setText(message, "utf-8", "html")
    //    Transport.send(msg)
  }
}