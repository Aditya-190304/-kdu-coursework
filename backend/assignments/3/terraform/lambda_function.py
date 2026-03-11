import json
import boto3
from decimal import Decimal

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('VisitorCounter_Aditya_Nigam')

class DecimalEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Decimal):
            return int(obj)
        return super(DecimalEncoder, self).default(obj)

def lambda_handler(event, context):
    headers = {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "GET, PUT, OPTIONS", # Allows PUT
        "Access-Control-Allow-Headers": "Content-Type"
    }
    
    try:
        # Check for method in v1 or v2 format
        http_method = event.get('httpMethod') 
        if not http_method:
             http_method = event.get('requestContext', {}).get('http', {}).get('method')
        
        # 1. OPTIONS (Browser Security Check)
        if http_method == 'OPTIONS':
            return {
                'statusCode': 200,
                'headers': headers,
                'body': json.dumps('OK')
            }
            
        # 2. GET (Read the count)
        if http_method == 'GET':
            response = table.get_item(Key={'id': 'counter'})
            count = response.get('Item', {}).get('count', 0)
            return {
                'statusCode': 200,
                'headers': headers,
                'body': json.dumps({'count': count}, cls=DecimalEncoder)
            }
            
        # 3. PUT (Add to the count)
        if http_method == 'PUT':
            response = table.update_item(
                Key={'id': 'counter'},
                UpdateExpression="ADD #c :inc",
                ExpressionAttributeNames={'#c': 'count'},
                ExpressionAttributeValues={':inc': 1},
                ReturnValues="UPDATED_NEW"
            )
            count = response['Attributes']['count']
            return {
                'statusCode': 200,
                'headers': headers,
                'body': json.dumps({'count': count}, cls=DecimalEncoder)
            }
            
        return {
            'statusCode': 400,
            'headers': headers,
            'body': json.dumps({'error': 'Method not allowed'})
        }

    except Exception as e:
        return {
            'statusCode': 500,
            'headers': headers,
            'body': json.dumps({'error': str(e)})
        }