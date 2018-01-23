package silen.scheduler.utils.runtime

import java.text.SimpleDateFormat
import java.util.Date

object DateTimeUtl {

  val DEFAULT_DATETIME_FMT = "yyyy/MM/dd HH:mm:ss"

  var formater = new SimpleDateFormat(DEFAULT_DATETIME_FMT)

  def currentDateTime(fmt: String = null) = {

    if (fmt != null) {

      formater = new SimpleDateFormat(fmt)

    }

    formater.format(new Date())

  }
}