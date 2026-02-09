resource "aws_dynamodb_table" "counter_table" {
  name           = "VisitorCounter_Aditya_Nigam"
  billing_mode   = "PAY_PER_REQUEST"
  hash_key       = "id"

  attribute {
    name = "id"
    type = "S"
  }
}