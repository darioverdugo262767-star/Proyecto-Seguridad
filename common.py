import json
from datetime import datetime

MAX_CLIENTS = 6

def timestamp():
    return datetime.now().strftime("%d/%m/%Y %H:%M:%S")

def make_msg(msg_type, sender='', to='ALL', text=''):
    message_dict = {
        "type": msg_type,
        "from": sender,
        "to": to,
        "text": text,
        "timestamp": timestamp()
    }
    return json.dumps(message_dict).encode('utf-8')

def parse_msg(bytes_msg):
    try:
        return json.loads(bytes_msg.decode('utf-8'))
    except Exception:
        return None