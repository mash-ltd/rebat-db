package com.mashltd.rebatdb

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.status.InfoStatus
import ch.qos.logback.core.status.StatusManager


// Much of the code was take from loglady's
// https://github.com/dln/loglady/blob/master/src/main/scala/org/eintr/loglady/Logger.scala
// 
//
// Provides a single point for logging
object Log {
  protected lazy val logger = LoggerFactory.getLogger("rebat-db").asInstanceOf[ch.qos.logback.classic.Logger]
  
  // Configure logback
  configure

  /** A function that logs a message with an exception */
  protected type LogFunc = (String, Throwable) => Unit

  // Exposes some config of underlying logger
  def isTraceEnabled = logger.isTraceEnabled
  def isDebugEnabled = logger.isDebugEnabled
  def isInfoEnabled = logger.isInfoEnabled
  def isWarnEnabled = logger.isWarnEnabled
  def isErrorEnabled = logger.isErrorEnabled

  /** Format a string using params, if any, otherwise use the string as-is */
  @inline protected final def format(fmt: String, params: Seq[Any]) = {
    if (params.nonEmpty) fmt.format(params:_*) else fmt
  }

  /** Log trace message */
  def trace(message: String,  params: Any*) {
    if (logger.isTraceEnabled) logTrace(format(message, params), null)
  }

  /** Log trace message with an exception */
  def trace(thrown: Throwable, message: String,  params: Any*) {
    if (logger.isTraceEnabled) logTrace(format(message, params), thrown)
  }

  /** Log debug message */
  def debug(message: String,  params: Any*) {
    if (logger.isDebugEnabled) logDebug(format(message, params), null)
  }

  /** Log debug message with an exception */
  def debug(thrown: Throwable, message: String,  params: Any*) {
    if (logger.isDebugEnabled) logDebug(format(message, params), thrown)
  }

  /** Log info message */
  def info(message: String,  params: Any*) {
    if (logger.isInfoEnabled) logInfo(format(message, params), null)
  }

  /** Log info message with an exception */
  def info(thrown: Throwable, message: String,  params: Any*) {
    if (logger.isInfoEnabled) logInfo(format(message, params), thrown)
  }

  /** Log warn message */
  def warn(message: String,  params: Any*) {
    if (logger.isWarnEnabled) logWarn(format(message, params), null)
  }

  /** Log warning message with an exception */
  def warn(thrown: Throwable, message: String,  params: Any*) {
    if (logger.isWarnEnabled) logWarn(format(message, params), thrown)
  }

  /** Log error message */
  def error(message: String,  params: Any*) {
    if (logger.isErrorEnabled) logError(format(message, params), null)
  }

  /** Log error message with an exception */
  def error(thrown: Throwable, message: String,  params: Any*) {
    if (logger.isErrorEnabled) logError(format(message, params), thrown)
  }

  protected def configure() {
    logger.setLevel(ch.qos.logback.classic.Level.TRACE);

    val lc:LoggerContext = LoggerFactory.getILoggerFactory().asInstanceOf[LoggerContext];

    val sm:StatusManager = lc.getStatusManager()
    if(sm != null)  {
     sm.add(new InfoStatus("Setting up rebat-db configuration.", lc))
    }

    val fa:FileAppender[ILoggingEvent] = new FileAppender[ILoggingEvent]()
    fa.setContext(lc)
    fa.setName("rebat-db-log")
    fa.setFile(Configuration.log_file)
    fa.setAppend(true)

    val pl:PatternLayoutEncoder = new PatternLayoutEncoder()
    pl.setContext(lc)
    pl.setPattern("%d{HH:mm:ss.SSS} %-5level %logger{36} - %msg%n")
    pl.start()
    
    fa.setEncoder(pl)
    fa.start()

    val rootLogger = lc.getLogger(Logger.ROOT_LOGGER_NAME)
    rootLogger.addAppender(fa)
  }

  protected val logTrace: LogFunc = logger.trace(_, _)
  protected val logDebug: LogFunc = logger.debug(_, _)
  protected val logInfo: LogFunc  = logger.info(_, _)
  protected val logWarn: LogFunc  = logger.warn(_, _)
  protected val logError: LogFunc = logger.error(_, _)
}
