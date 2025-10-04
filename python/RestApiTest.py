import base64
import json
import ssl
import unittest
import http.client


class InventoryItems(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        # Basic Auth User credentials
        username = "user"
        password = "password"
        credentials = f"{username}:{password}"
        encoded_credentials_user = base64.b64encode(credentials.encode()).decode()

        # Basic Auth User Admin credentials
        username = "admin"
        password = "password"
        credentials = f"{username}:{password}"
        encoded_credentials_admin = base64.b64encode(credentials.encode()).decode()

        # Set headers with Authorization
        cls.headers_user = {
            "Authorization": f"Basic {encoded_credentials_user}"
        }

        cls.headers_admin = {
            "Authorization": f"Basic {encoded_credentials_admin}"
        }

    def test_user_get_inventoryItems(self):
        # Set up the expected response
        expected = [
            {
                "id": 1,
                "description": "Pure and Natural Sour Cream",
                "barcode": "0007342000011",
                "quantity": 16,
                "brand": "Daisy",
                "category": "Dairy",
                "unit": "oz"
            },
            {
                "id": 2,
                "description": "Premium Pork Regular Breakfast Sausage Roll",
                "barcode": "0007790011553",
                "quantity": 16,
                "brand": "Jimmy Dean",
                "category": "Meat",
                "unit": "oz"
            }
        ]

        # Open a connection and save off the response and body to separate variables
        conn = http.client.HTTPSConnection("localhost", 443, context=ssl._create_unverified_context())
        conn.request("GET", "/inventory-items", headers=self.headers_user)
        response = conn.getresponse()
        body = response.read().decode()
        conn.close()

        # Assert that response status and body is what we expect
        self.assertEqual(response.status, 200)
        self.assertEqual(json.loads(body), expected)

    def test_user_get_inventoryItems_id(self):
        expected = {
            "id": 1,
            "description": "Pure and Natural Sour Cream",
            "barcode": "0007342000011",
            "quantity": 16,
            "brand": "Daisy",
            "category": "Dairy",
            "unit": "oz"
        }

        conn = http.client.HTTPSConnection("localhost", 443, context=ssl._create_unverified_context())
        conn.request("GET", "/inventory-items/id/1", headers=self.headers_user)
        response = conn.getresponse()
        body = response.read().decode()
        conn.close()

        self.assertEqual(response.status, 200)
        self.assertEqual(json.loads(body), expected)

    def test_user_get_inventoryItems_barcode(self):
        expected = {
            "id": 1,
            "description": "Pure and Natural Sour Cream",
            "barcode": "0007342000011",
            "quantity": 16,
            "brand": "Daisy",
            "category": "Dairy",
            "unit": "oz"
        }

        conn = http.client.HTTPSConnection("localhost", 443, context=ssl._create_unverified_context())
        conn.request("GET", "/inventory-items/barcode/0007342000011", headers=self.headers_user)
        response = conn.getresponse()
        body = response.read().decode()
        conn.close()

        self.assertEqual(response.status, 200)
        self.assertEqual(json.loads(body), expected)

    def test_user_post_inventoryItems(self):
        payload = {
            "id": 1,
            "description": "Pure and Natural Sour Cream",
            "barcode": "0007342000011",
            "quantity": 16,
            "brand": "Daisy",
            "category": "Dairy",
            "unit": "oz"
        }
        json_payload = json.dumps(payload)

        header = self.headers_user
        header["Content-Type"] = "application/json"

        conn = http.client.HTTPSConnection("localhost", 443, context=ssl._create_unverified_context())
        conn.request("POST", "/inventory-items", body=json_payload, headers=header)
        response = conn.getresponse()
        conn.close()

        self.assertEqual(response.status, 403)

    def test_user_put_inventoryItems_id(self):
        payload = {
            "id": 1,
            "description": "Pure and Natural Sour Cream",
            "barcode": "0007342000011",
            "quantity": 16,
            "brand": "Daisy",
            "category": "Meat",
            "unit": "oz"
        }
        json_payload = json.dumps(payload)

        header = self.headers_user
        header["Content-Type"] = "application/json"

        conn = http.client.HTTPSConnection("localhost", 443, context=ssl._create_unverified_context())
        conn.request("PUT", "/inventory-items/id/1", body=json_payload, headers=header)
        response = conn.getresponse()
        conn.close()

        self.assertEqual(response.status, 403)

    def test_user_delete_inventoryItems_id(self):

        conn = http.client.HTTPSConnection("localhost", 443, context=ssl._create_unverified_context())
        conn.request("DELETE", "/inventory-items/id/1", headers=self.headers_user)
        response = conn.getresponse()
        conn.close()

        self.assertEqual(response.status, 403)


if __name__ == '__main__':
    unittest.main()
