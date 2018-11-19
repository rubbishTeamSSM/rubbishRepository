package com.neusoft.sdd.base.exception;

import org.apache.log4j.Logger;

public class IErroNodeException extends IRuntimeException
{
  private static final long serialVersionUID = 1794343867L;
  private Logger logger;

  public IErroNodeException()
  {
    this.logger = Logger.getLogger(getClass());
  }

  public IErroNodeException(String message)
  {
    super(message);

    this.logger = Logger.getLogger(getClass());

    this.logger.error("IMoneyNotEnoughException : " + message);
  }

  public IErroNodeException(Throwable cause) {
    super(cause);

    this.logger = Logger.getLogger(getClass());

    this.logger.error("IMoneyNotEnoughException : " + cause);
  }

  public IErroNodeException(String message, Throwable cause) {
    super(message, cause);

    this.logger = Logger.getLogger(getClass());

    this.logger.error("IMoneyNotEnoughException : " + message, cause);
  }
}