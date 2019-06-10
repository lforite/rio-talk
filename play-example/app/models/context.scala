package models

case class CorrelationId(value: String) extends AnyVal

case class RequestContext(
    correlationId: CorrelationId
)
