package data;

public interface ToSolve {
	public static int[][][][] getUnsolved() {
		return PIECES1;
	}

	int[][][] CUBE1 = {
		{
			{3,3,2},
			{3,1,2},
			{3,2,2},
		},
		{
			{3,4,2},
			{1,1,5},
			{1,5,5},
		},
		{
			{4,4,4},
			{4,5,4},
			{1,5,5},
		},
	};

	int[][][][] PIECES1 = {
		{//brown
			{
				{0,1,0},
				{0,1,1},
			},
			{
				{1,1,0},
				{0,0,0},
			},
		},
		{//cyan
			{
				{0,0,2},
				{2,2,2},
			},
			{
				{0,0,0},
				{2,0,0},
			}
		},
		{//orange
			{
				{3,0,0},
				{3,3,3},
			},
			{
				{0,0,0},
				{3,0,0},
			},
		},
		{//red
			{
				{4,0,4},
				{4,4,4},
			},
			{
				{0,0,0},
				{0,4,0},
			},
		},
		{//white
			{
				{0,5},
				{5,5},
			},
			{
				{5,0},
				{5,5},
			},
		},

	};
}
