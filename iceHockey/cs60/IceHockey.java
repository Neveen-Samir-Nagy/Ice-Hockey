package eg.edu.alexu.csd.datastructure.iceHockey.cs60;

import java.awt.Point;
import eg.edu.alexu.csd.datastructure.iceHockey.IPlayersFinder;

/**
 * . project
 *
 * @author EL-hamd
 *
 */
public class IceHockey implements IPlayersFinder {

	/**
	 * . define
	 */
	public static final int MAX = 100;

	/**
	 * . define 2
	 */
	public static final int M = 4;

	/**
	 * . colmnrow
	 */
	private int coulmn,
	row, i = 0, j = 0,
	count = 0,
	bigcoulmn = 0,
	bigrow = 0,
	smallcoulmn = 0,
	smallrow = 0,
			size = 0, call = 0, i1 = 0, j1 = 0;

	/**
	 * . array
	 */
	private int[][] arrayfind = new int[MAX][MAX];

	/**
	 * . boolean
	 */
	private Boolean[][] array;

	/**
	 * . char
	 */
	private char[][] photo1;

	/**
	 * . function
	 *
	 * @param team
	 *            first
	 * @param indexrow
	 *            second
	 * @param indexcoulmn
	 *            third
	 */
	private void check(
			final int team,
			final int indexrow,
			final int indexcoulmn) {
		if (indexrow < row
				&&
				indexcoulmn < coulmn
				&&
				indexrow >= 0
				&&
				indexcoulmn >= 0) {
			if (!array[indexrow][indexcoulmn]) {
				if (
						Character.isDigit(
								photo1
								[indexrow][indexcoulmn]))
				{
					call =
							Character.
							getNumericValue(
									photo1
									[indexrow]
											[indexcoulmn]);
					if (call == team) {
						array[indexrow][indexcoulmn] =
								true;
						count++;
						if (bigcoulmn < indexcoulmn) {
							bigcoulmn = indexcoulmn;
						}
						if (smallcoulmn > indexcoulmn) {
							smallcoulmn =
									indexcoulmn;
						}
						if (bigrow < indexrow) {
							bigrow = indexrow;
						}
						if (smallrow > indexrow) {
							smallrow = indexrow;
						}
						check(team,
								indexrow,
								indexcoulmn
								+
								1);
						check(team,
								indexrow,
								indexcoulmn
								-
								1);
						check(team,
								indexrow + 1,
								indexcoulmn);
						check(team,
								indexrow - 1,
								indexcoulmn);
					}
				}
			}
		}
	}

	/**
	 * . function
	 */
	private void arrange() {
		for (i1 = 0; i1 < size; i1++) {
			for (j1 = i1 + 1; j1 < size; j1++) {
				int tempx = 0, tempy = 0;
				if (arrayfind[i1][0] > arrayfind[j1][0]) {
					tempx = arrayfind[i1][0];
					arrayfind[i1][0] = arrayfind[j1][0];
					arrayfind[j1][0] = tempx;
					tempy = arrayfind[i1][1];
					arrayfind[i1][1] = arrayfind[j1][1];
					arrayfind[j1][1] = tempy;
				} else if (arrayfind[i1][0]
						==
						arrayfind[j1][0]) {
					if (arrayfind[i1][1]
							>
					arrayfind[j1][1]) {
						tempx = arrayfind[i1][0];
						arrayfind[i1][0] =
								arrayfind
								[j1][0];
						arrayfind[j1][0] = tempx;
						tempy = arrayfind[i1][1];
						arrayfind[i1][1] =
								arrayfind
								[j1][1];
						arrayfind[j1][1] = tempy;
					}
				}
			}
		}
	}

	@Override
	public final Point[] findPlayers(
			final String[] photo,
			final int team,
			final int threshold) {
		if (photo == null || photo.length == 0) {
			return null;
		}
		size = 0;
		row = photo.length;
		coulmn = photo[0].length();
		photo1 = new char[row][coulmn];
		array = new Boolean[row][coulmn];
		for (i = 0; i < row; i++) {
			for (j = 0; j < coulmn; j++) {
				array[i][j] = false;
			}
		}

		for (i = 0; i < row; i++) {
			for (j = 0; j < coulmn; j++) {
				photo1[i][j] = photo[i].charAt(j);
			}
		}
		for (i = 0; i < row; i++) {
			for (j = 0; j < coulmn; j++) {
				if (!array[i][j]) {
					if (Character.isDigit(photo1[i][j])) {
						call =
								Character.
								getNumericValue(
										photo1
										[i][j]);
						if (call == team) {
							count = 0;
							smallrow = i;
							bigrow = i;
							smallcoulmn = j;
							bigcoulmn = j;
							check(team, i, j);
							if ((count * M) >=
									threshold) {
								int x = (((
										smallrow * 2)
										+ (
												bigrow * 2))
										/ 2) +
										1;
								int y = (((
										smallcoulmn * 2)
										+ (
												bigcoulmn * 2))
										/ 2) +
										1;
								arrayfind
								[size][0] =
										y;
								arrayfind
								[size][1] =
										x;
								size++;
							}
						}
					}
				}
			}
		}
		arrange();
		Point[] line = new Point[size];
		for (i = 0; i < size; i++) {
			line[i] = (new Point(arrayfind[i][0], arrayfind[i][1]));
		}
		return line;
	}
}
