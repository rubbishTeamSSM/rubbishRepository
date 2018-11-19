package com.neusoft.sdd.base.exception;

import org.apache.log4j.Logger;

public class IFeeCancelException extends IRuntimeException
{
  private static final long serialVersionUID = 1794343867L;
  private Logger logger;

  public IFeeCancelException()
  {
    this.logger = Logger.getLogger(getClass());
  }

  public IFeeCancelException(String message)
  {
    super(message);

    this.logger = Logger.getLogger(getClass());

    this.logger.error("IMoneyNotEnoughException : " + message);
  }

  public IFeeCancelException(Throwable cause) {
    super(cause);

    this.logger = Logger.getLogger(getClass());

    this.logger.error("IMoneyNotEnoughException : " + cause);
  }

  public IFeeCancelException(String message, Throwable cause) {
    super(message, cause);

    this.logger = Logger.getLogger(getClass());

    this.logger.error("IMoneyNotEnoughException : " + message, cause);
  }
}