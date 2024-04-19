import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * This program calculates sales commission for all sales associates and sorts them from lowest to highest.
 *
 * @author Al Hochbaum
 * @version 1.0
 */
public class UsedCars
{
	// ID and Name 2D Array
	public final static String[] [] ID_AND_NAMES = { {"100", "150", "200", "350", "375", "400", "500", "535", "550", "575", "600", "610"},
														{"Candy Cain", "Amanda Linn", "Petey Cruzer", "Patty O'Lantern","Harry Armes",
															"Dan Delyon", "June Ipperbush", "Willibold Feival","Ivanna Karr",
																"Holly Hox", "Dewey Cheatum", "Anna Gramm",}
													};

	// Main user interface
	public static void main(String[] args)
	{

		// Accumulator array for associate's commission
		double[] commissionTotals = new double[ID_AND_NAMES[0].length];

		// 1 - dimensional index array to help sort the data
		int[] indexArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

		// Scanner format object
		Scanner keyboard = new Scanner(System.in);

		// Decimal format object.
		DecimalFormat dollar = new DecimalFormat("$#,##0.00");

		// Porgram header
		String spacing = "",
			   header = String.format("%-17s%s%n%-16s%s%n%-16s%s%n%n",
										spacing,
										"JavaAuto Used Cars",
										spacing,
										"Commissions Earned",
										spacing,
										"Programmed by: Al Hochbaum");

		System.out.printf(header);

		// Local Variables
		int IDfromSearch = -1,
		    counter = 0;
		double carSales = 0.00,
		       totalSales = 0.00;

		// This is the main loop, it continues until the user enters 0 as a sales ID
		do
		{
			IDfromSearch = inputSalesID(keyboard);

			for(int i = 0; IDfromSearch != 0 && i < ID_AND_NAMES[0].length; i++)
			{
				if(IDfromSearch == Integer.parseInt(ID_AND_NAMES[0][i]))
				{
					carSales = inputSalesAmt(keyboard);
					commissionTotals[i] =  calcCommission(carSales);
				}
			}

			counter++;
		} while(IDfromSearch != 0 && counter + 1 != ID_AND_NAMES[0].length);

		String mainHeader = String.format("%n%-15s%-27s%-15s%n%n", "ID", "ASSOCIATE", "COMMISSION");

		// This for loop displays and formats the table used to display commission data in a sequential manner
		for(int i = 0; i < ID_AND_NAMES[0].length; i++)
		{
			System.out.printf("%s%-15s%-27s%-15s%n",
							  i == 0 ? mainHeader : "",
							  ID_AND_NAMES[0][i],
							  ID_AND_NAMES[1][i],
							  String.format("%.2f", commissionTotals[i])
			);

			totalSales += commissionTotals[i];
		}

		// Display total commission
		System.out.println("\nTotal Commissions Earned: " + dollar.format(totalSales) + "\n");

		sort(commissionTotals, indexArray);

		// This for loop displays and formats the table used to display commission data in a sequential manner
		for(int x = 0; x < ID_AND_NAMES[0].length; x++)
		{
			System.out.printf("%s%-15s%-27s%-15s%n",
							  x == 0 ? mainHeader : "",
							  ID_AND_NAMES[0][indexArray[x]],
							  ID_AND_NAMES[1][indexArray[x]],
							  String.format("%.2f", commissionTotals[indexArray[x]])
			);
		}

		// Display total commission
		System.out.println("\nTotal Commissions Earned: " + dollar.format(totalSales) + "\n");
	}

	// To input and validate the sales associate ID
	public static int inputSalesID(Scanner reader)
	{
		int returnedID = 0,
			ID = 0;

		String startingPrompt = String.format("%nEnter sales ID <0 to quit>: "),
			   secondaryPrompt = "";

		do
		{
			secondaryPrompt = String.format("%nSales ID %d does not exist on file%n%nEnter sales ID <0 to quit>: ", ID);

			System.out.printf("%s", returnedID != -1 ? startingPrompt : secondaryPrompt);
			ID = Integer.parseInt(reader.nextLine());

			returnedID = search(ID_AND_NAMES[0], ID);

		} while(returnedID == -1 && ID != 0);

		return ID;
	}

	// To input and validate the sales amount value
	public static double inputSalesAmt(Scanner reader)
	{
		double salesAmount = 0;

		String startingPrompt = "Enter sales amount: ",
			   secondaryPrompt = String.format("%nSales amount must be between 0 and 99999%n%nEnter sales amount: ");

		do
		{
			System.out.printf("%s", salesAmount == 0 ?
				startingPrompt
				: secondaryPrompt
			);

			salesAmount = Double.parseDouble(reader.nextLine());
		} while(salesAmount < 0 || salesAmount > 99999.00);

		return salesAmount;
	}

	// To calculate each sales associate’s commission amount
	public static double calcCommission(double sales)
	{
		// Amount ranges
		double[] salesPerVehicleRange = {10000, 20000, 40000, 50000, 99999};

		// Commission rates
		double[] commissionRates = {0.01, 0.02, 0.03, 0.05, 0.08};

		int indexfound = search(salesPerVehicleRange, sales);

		double percentageRate = commissionRates[indexfound];

		return sales * percentageRate;
	}

	// Overloaded method 1 - that returns the index of for the paralle arrays
	public static int search(double[] findIndex, double employeeSales)
	{
		int indexToParalleArray = -1;

		for(int i = 0; indexToParalleArray == -1 && i < findIndex.length; i++)
		{
			if(employeeSales <= findIndex[i])
			{
				indexToParalleArray = i;
			}
		}

		return indexToParalleArray;
	}

	// Overloaded method 2 - Validate the ID entered
	public static int search(String[] IDAndNames, int salesID)
	{
		int validID = -1;

		for(int i = 0; salesID != 0 && validID == -1 && i < IDAndNames.length; i++)
		{
			if(salesID == Integer.parseInt(IDAndNames[i]))
			{
				validID = Integer.parseInt(IDAndNames[i]);
			}
		}

		// Return the position of the item, or -1 if it was not found.
		return validID;
	}

	// Method that  sorts the data entered from highest to lowest and accepts two 1-dim arrays as parameters and returns no value.
	public static void sort(double[] commissionTotals, int[] indexArray)
	{
		int temporary = 0,
			storageLocation = 0;
		double switchingSales = 0;
		double[] copyComissionsArray = new double[commissionTotals.length];
		double value = 0;


		// Copying the commissions array.
		for(int i = 0; i < copyComissionsArray.length; i++)
		{
			copyComissionsArray[i] = commissionTotals[i];
		}

		//selects position to have the lowest value
		for(int index = 0; index < copyComissionsArray.length; index++)
		{

			for(int j = index + 1; j < copyComissionsArray.length ; j++)
			{
				if(copyComissionsArray[index] < copyComissionsArray[j])
				{
					storageLocation = j;

					//switch lowest(minimum) value into pos i
					switchingSales = copyComissionsArray[index];
					copyComissionsArray[index] = copyComissionsArray[j];
					copyComissionsArray[j] = switchingSales;
				}
			}

			value = copyComissionsArray[index];

			if(value != 0.00)
			{
				for(int x = 0; x < indexArray.length; x++)
				{
					if(commissionTotals[x] == value)
					{
						System.out.println();

						System.out.println();

						System.out.println();
						temporary = indexArray[index];
						indexArray[index] = x;
						indexArray[storageLocation] = temporary;
					}
				}
			}
		}
	}
}