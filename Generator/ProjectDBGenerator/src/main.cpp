#include <fstream>
#include <string>

int measurement = 1;

void writeDay(int month, int day, std::ofstream& file)
{
	for (int k = 1; k < 11; ++k)
	{
		file << "INSERT INTO measurements(measurement_id, value, time, type, station_id)\nVALUES(";
		file << measurement << ", " << 15 + rand() % 30 << ", CAST('2020-" << month << "-" << day << " 12:00:00.000' AS datetime)";
		file << ", 'PM10', " << k << ");\n";
		measurement++;
		file << "INSERT INTO measurements(measurement_id, value, time, type, station_id)\nVALUES(";
		file << measurement << ", " << 20 + rand() % 40 << ", CAST('2020-" << month << "-" << day << " 12:00:00.000' AS datetime)";
		file << ", 'PM2,5', " << k << ");\n";
		measurement++;
	}
}

int main()
{
	std::string stations[10] = { "Nowa Huta", "AGH", "£agiewniki", "Bronowice", "Pr¹dnik Bia³y", "Pr¹dnik Czerwony", "Podgórze", "Czy¿yny", "Mistrzejowice", "Dêbniki" };
	float locations[20] = { 50.072424, 20.119335,
							50.068138, 19.912728,
							50.019670, 19.937417,
							50.080828, 19.871431,
							50.091264, 19.923450,
							50.085594, 19.972561,
							50.037627, 20.015381,
							50.063996, 20.014970,
							50.095061, 20.012128,
							50.006743, 19.873870 };
	std::ofstream file("inserts.txt");
	for (int i = 1; i < 11; ++i)
	{
		file << "INSERT INTO stations(station_id, name, location_x, location_y)\nVALUES(" << i << ", '" << stations[i - 1] << "', ";
		file << locations[2 * (i - 1)] << ", " << locations[2 * (i - 1) + 1] << ");\n";
	}
	for (int i = 5; i < 7; ++i)
	{
		if(i % 2 == 1)
		{
			for (int j = 1; j < 32; ++j)
			{
				writeDay(i, j, file);
			}
		}
		else
		{
			for (int j = 1; j < 31; ++j)
			{
				writeDay(i, j, file);
			}
		}
	}
	file << "INSERT INTO norms(norm_id, value, type)\nVALUES(1, 40, 'PM10');\n";
	file << "INSERT INTO norms(norm_id, value, type)\nVALUES(2, 25, 'PM2,5');\n";
}